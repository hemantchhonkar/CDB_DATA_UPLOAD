package maf.c4c.dataupload;

import maf.c4c.dataupload.thread.Processors;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntryPoint {
    final static Logger logger = Logger.getLogger(EntryPoint.class);
    public static void main(String[] args) throws IOException {
        String DEFAULT_BASE_DIR = "/opt/application/CDB_DATA_MIGRATION/files/";
        String DEFAULT_FILE_EXT = "csv";
        int DEFAULT_NO_OF_CONSUMERS = 100;
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        // Reading data using readLine
        System.out.println("Please enter file directory full path");
        String baseDir = reader.readLine();
        if(baseDir == null || baseDir.length()<=0){
            logger.info("No file directory entered, going for the default : "+DEFAULT_BASE_DIR);
            baseDir = DEFAULT_BASE_DIR;
        }
        System.out.println("Please enter file extension to be processed.");
        String fileExt = reader.readLine();
        if(fileExt == null || fileExt.length() <= 0){
            logger.info("No file Extension entered, going for the default : "+DEFAULT_FILE_EXT);
            baseDir = DEFAULT_FILE_EXT;
        }
        System.out.println("Please enter number of consumer threads you want to start.");
        int noOfConsumers = 0;
        try
        {
            noOfConsumers =  Integer.parseInt(reader.readLine());
            if(noOfConsumers <= 0){
                logger.error("Invalid no of threads entered. going for default "+DEFAULT_NO_OF_CONSUMERS);
                noOfConsumers = DEFAULT_NO_OF_CONSUMERS;
            }
        }
        catch (NumberFormatException e){
            logger.error("Invalid no of threads entered. going for default "+DEFAULT_NO_OF_CONSUMERS);
            noOfConsumers = DEFAULT_NO_OF_CONSUMERS;
        }
        // Printing the read line
        logger.info(baseDir);
        logger.info(fileExt);
        logger.info(noOfConsumers);
        logger.info("Data migration process started.....");
        Processors.startMigration(baseDir, fileExt, noOfConsumers);
    }
}
