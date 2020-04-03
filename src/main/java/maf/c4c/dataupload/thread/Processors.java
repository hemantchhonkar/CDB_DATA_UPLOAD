package maf.c4c.dataupload.thread;

import maf.c4c.dataupload.service.CustomerService;
import maf.c4c.dataupload.model.CustomerInfo;
import maf.c4c.dataupload.service.CustomerServiceSynch;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Processors implements Runnable {
    final static Logger logger = Logger.getLogger(Processors.class);
    public static String BASE_DIR = "";
    final String INPUT_DIR = BASE_DIR+"/input/";
    final String INPROCESS_DIR = BASE_DIR+"/inprocess/";
    final String PROCESSED_DIR = BASE_DIR+"/processed/";
    private static final int CONSUMER_COUNT = 50;
    private final static BlockingQueue<CustomerInfo> linesReadQueue = new ArrayBlockingQueue<>(100);

    private boolean isConsumer;
    private static boolean producerIsDone = false;

    public Processors(boolean consumer) {
        this.isConsumer = consumer;
    }

    public static void startMigration(String baseDirPath) {
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
                    paths.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".csv") )
                            .map(path -> path.getFileName().toString()).collect(Collectors.toList());
            if(fileNameList.size() > 0) {
                fileNameList.forEach(fileName ->  processFile(fileName));
            }
            else {
                logger.error("No files found in the directory "+INPUT_DIR);
            }
        } catch (IOException ioe){
            logger.error(" Exception processFiles", ioe);
        }
        producerIsDone = true;
        logger.info(Thread.currentThread().getName() + " producer is done");
    }

    private void processFile(String inputFileName) {
        try {
            moveFile(inputFileName, INPUT_DIR, INPROCESS_DIR);
            CSVParser parser = new CSVParser(new FileReader(INPROCESS_DIR+inputFileName), CSVFormat.DEFAULT.withHeader());
            parser.getRecords().forEach(record -> {
                logger.info("read=" + record);
                try {
                    linesReadQueue.put(new CustomerInfo(record));
                } catch (InterruptedException e) {
                    logger.error(" processFile interrupted ", e);
                }
            });
            moveFile(inputFileName, INPROCESS_DIR, PROCESSED_DIR);
        } catch (IOException e) {
            logger.error(" Exception processFile ", e);
        }
    }

    private void moveFile(String inputFileName, String input_dir, String inprocess_dir) throws IOException {
//    	try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			logger.error("Error while putting the thread to sleep in moveFile method", e);
//		}
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
                CustomerServiceSynch.process(customerInfo);
                logger.info("procesed:" + customerInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info(Thread.currentThread().getName() + " consumer is done");
    }
}