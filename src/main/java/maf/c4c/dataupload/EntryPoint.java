package maf.c4c.dataupload;

import maf.c4c.dataupload.thread.Processors;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntryPoint {
    final static Logger logger = Logger.getLogger(EntryPoint.class);
    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        System.out.println("Please enter file directory full path");
        String baseDir = reader.readLine();
        System.out.println("Please enter file extension to be processed.");
        String fileExt = reader.readLine();

        // Printing the read line
        logger.info(baseDir);
        logger.info("Data migration process started.....");
        Processors.startMigration(baseDir, fileExt);
    }
}
