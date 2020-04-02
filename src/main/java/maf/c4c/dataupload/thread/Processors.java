package maf.c4c.dataupload.thread;

import maf.c4c.dataupload.service.CustomerService;
import maf.c4c.dataupload.model.CustomerInfo;
import maf.c4c.dataupload.service.CustomerServiceSynch;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

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
    public static String BASE_DIR = "";
    final String INPUT_DIR = BASE_DIR+"/input/";
    final String INPROCESS_DIR = BASE_DIR+"/inprocess/";
    final String PROCESSED_DIR = BASE_DIR+"/processed/";
    private static final int CONSUMER_COUNT = 30;
    private final static BlockingQueue<CustomerInfo> linesReadQueue = new ArrayBlockingQueue<>(30);

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
        System.out.println("Total elapsed time: " + elapsedTimeInMillis + " ms");
    }

    private void processFiles() throws IOException {

        try (Stream<Path> paths = Files.walk(Paths.get(INPUT_DIR))) {
            List<String> fileNameList =
                    paths.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".csv") )
                            .map(path -> path.getFileName().toString()).collect(Collectors.toList());
            if(fileNameList.size() > 0) {
                fileNameList.forEach(fileName ->  processFile(fileName));
            }
            else {
                System.out.println("No files found in the directory "+INPUT_DIR);
            }
        }
        producerIsDone = true;
        System.out.println(Thread.currentThread().getName() + " producer is done");
    }

    private void processFile(String inputFileName) {
        try {
            CSVParser parser = new CSVParser(new FileReader(INPUT_DIR+inputFileName), CSVFormat.DEFAULT.withHeader());
            moveFile(inputFileName, INPUT_DIR, INPROCESS_DIR);
            parser.getRecords().forEach(record -> {
                System.out.println("read=" + record);
                try {
                    linesReadQueue.put(new CustomerInfo(record));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            moveFile(inputFileName, INPROCESS_DIR, PROCESSED_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveFile(String inputFileName, String input_dir, String inprocess_dir) throws IOException {
//        Files.move(Paths.get(input_dir + inputFileName),
//                Paths.get(inprocess_dir + inputFileName),
//                StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void run() {
        if (isConsumer) {
            consume();
        } else {
            try {
                processFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void consume() {
        System.out.println("Consumer started....:");
        try {
            while (!producerIsDone || (producerIsDone && !linesReadQueue.isEmpty())) {
                CustomerInfo customerInfo = linesReadQueue.take();
                CustomerServiceSynch.process(customerInfo);
                System.out.println("procesed:" + customerInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " consumer is done");
    }
}