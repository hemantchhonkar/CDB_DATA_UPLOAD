package maf.c4c.dataupload.thread;

import maf.c4c.dataupload.model.CustomerInfo;
import maf.c4c.dataupload.service.CustomerServiceSynch;
import maf.c4c.dataupload.util.JSONUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.w3c.dom.Document;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Processors implements Runnable {
    private List<String> countryCodesToMigrate = Arrays.asList(new String[]{"AE", "BH", "KW"});
    final static Logger logger = Logger.getLogger(Processors.class);
    public static String BASE_DIR = "";
    final String INPUT_DIR = BASE_DIR+"/input/";
    final String INPROCESS_DIR = BASE_DIR+"/inprocess/";
    final String PROCESSED_DIR = BASE_DIR+"/processed/";
    private static final int CONSUMER_COUNT = 80;
    private static String defaultFileExtToProcess = ".csv";
    private static final List<String> allowedFileExtTOProcess = Arrays.asList(new String[]{"csv", "json"});
    private final static BlockingQueue<CustomerInfo> linesReadQueue = new ArrayBlockingQueue<>(300);

    private boolean isConsumer;
    private static boolean producerIsDone = false;

    public Processors(boolean consumer) {
        this.isConsumer = consumer;
    }

    public static void startMigration(String baseDirPath, String fileExtToProcess) {
        if(fileExtToProcess !=null && allowedFileExtTOProcess.contains(fileExtToProcess)){
            defaultFileExtToProcess = fileExtToProcess;
        } else {
            try {
                throw new Exception("Unsupported file extension "+fileExtToProcess);
            } catch (Exception e) {
                logger.error("Unsupported file extension "+fileExtToProcess, e);
                return;
            }

        }
        BASE_DIR = baseDirPath;
        long startTime = System.nanoTime();

        ExecutorService producerPool = Executors.newFixedThreadPool(1);
        producerPool.submit(new Processors(false));
        ExecutorService consumerPool = Executors.newFixedThreadPool(CONSUMER_COUNT);
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            consumerPool.submit(new Processors(true));
        }
        producerPool.shutdown();
        consumerPool.shutdown();

        while (!producerPool.isTerminated() && !consumerPool.isTerminated()) {
        }
        long endTime = System.nanoTime();
        long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        logger.info("Total elapsed time: " + elapsedTimeInMillis + " ms");
    }

    private void processFiles() {
        try (Stream<Path> paths = Files.walk(Paths.get(INPUT_DIR))) {
            List<String> fileNameList =
                    paths.filter(path -> Files.isRegularFile(path) && path.toString().endsWith("."+defaultFileExtToProcess.toLowerCase()) )
                            .map(path -> path.getFileName().toString()).collect(Collectors.toList());
            if(fileNameList.size() > 0) {
                fileNameList.forEach(fileName -> {
                    if(defaultFileExtToProcess.equals("csv")){
                        processCSVFile(fileName);
                    } else {
                        processJSONFile(fileName);
                    }

                });
            }
            else {
                logger.error("No files found in the directory "+INPUT_DIR);
            }
        } catch (IOException ioe){
            logger.error(" Exception processFiles", ioe);
        }
        //processCSVFiles();
        producerIsDone = true;
        logger.info(Thread.currentThread().getName() + " producer is done");
    }
    private void processJSONFiles(){

    }

    private void processJSONFile(String jsonFileName){
        logger.info("Processing JSON file: "+INPUT_DIR+"/"+jsonFileName);
        try {
            moveFile(jsonFileName, INPUT_DIR, INPROCESS_DIR);
            File jsonFile = new File(INPROCESS_DIR+jsonFileName);
            JsonFactory jsonfactory = new JsonFactory(); //init factory
            JsonParser parser = jsonfactory.createJsonParser(jsonFile); //create JSON parser
            JsonToken jsonToken = parser.nextToken();
            CustomerInfo customerInfo = new CustomerInfo();
            while (jsonToken!= JsonToken.END_ARRAY){
                JSONUtil.setFieldsFromJson(parser, customerInfo, jsonToken);
                if(jsonToken==JsonToken.END_OBJECT){
                    System.out.println("Customer from JSON : "+customerInfo);
                    try {
                        linesReadQueue.put(customerInfo);
                    } catch (InterruptedException e) {
                        logger.error(" Processing JSON file interrupted ", e);
                    }
                    customerInfo = new CustomerInfo();
                }
                jsonToken = parser.nextToken();
            }
            parser.close();
            logger.info("Processing JSON file END");
            moveFile(jsonFileName, INPROCESS_DIR, PROCESSED_DIR);
        } catch (IOException e) {
            logger.error(" Exception processJSONFile ", e);
        }
    }

    private void processCSVFile(String inputFileName) {
        try {
            logger.info("Processing CSV file: "+INPUT_DIR+"/"+inputFileName);
            moveFile(inputFileName, INPUT_DIR, INPROCESS_DIR);
            CSVParser parser = new CSVParser(new FileReader(INPROCESS_DIR+inputFileName), CSVFormat.DEFAULT.withHeader());
            parser.getRecords().forEach(record -> {
                logger.info("read=" + record);
                try {
                    linesReadQueue.put(new CustomerInfo(record));
                } catch (InterruptedException e) {
                    logger.error(" Processing CSV file interrupted ", e);
                }
            });
            moveFile(inputFileName, INPROCESS_DIR, PROCESSED_DIR);
        } catch (IOException e) {
            logger.error(" Exception processCSVFile ", e);
        }
    }

    private void moveFile(String inputFileName, String input_dir, String inprocess_dir) throws IOException {
        Files.move(Paths.get(input_dir + inputFileName),
                Paths.get(inprocess_dir + inputFileName),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void run() {
        if (isConsumer) {
            consume();
        } else {
            processFiles();
        }
    }

    private void consume() {
        logger.info("Consumer started....:");
        try {
            while (!producerIsDone || (producerIsDone && !linesReadQueue.isEmpty())) {
                CustomerInfo customerInfo = linesReadQueue.take();
                if(this.countryCodesToMigrate.contains(customerInfo.getCountryCode())) {
                    CustomerServiceSynch.process(customerInfo);
                }
                else{
                    logger.info("Ignoring as country code is out of scope. Data: ");
                }

                if(customerInfo.getCountryCode() ==null && customerInfo.getCountryCode().length() <= 0){
                    logger.error("Country code no found for the customer, check the failed json: "+ customerInfo);
                    writeFailedRecordsIntoAFile(customerInfo);
                }
                logger.info("procesed:" + customerInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info(Thread.currentThread().getName() + " consumer is done");
    }

    private static void writeFailedRecordsIntoAFile(CustomerInfo customerInfo) {
        try
        {
            FileWriter fw = new FileWriter(new File(Processors.BASE_DIR+"/failed/customer_without_country_code.json"), true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(customerInfo.toString());
            //Closing BufferedWriter Stream
            bw.close();
        } catch (IOException e) {
            logger.error("JSON Write failed customer_without_country_code", e);
        }
    }
}