package maf.c4c.dataupload;

import maf.c4c.dataupload.thread.Processors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntryPoint {

    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        System.out.println("Please enter file directory full path");
        String baseDir = reader.readLine();

        // Printing the read line
        System.out.println(baseDir);
        System.out.println("Data migration process started.....");
        Processors.startMigration(baseDir);
    }
}
