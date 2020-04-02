package maf.c4c.dataupload.util;

import maf.c4c.dataupload.model.Country;
import maf.c4c.dataupload.model.CustomerInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class CSVUtil {
    public static List<CustomerInfo> getCustomerInfoListFromCSV() throws IOException {
        String txtFileName = "/Users/hemantchhonkar/Documents/data_upload/files/input/c4ccustomers_1585589845064.csv";
        CSVParser parser = new CSVParser(new FileReader(txtFileName), CSVFormat.DEFAULT.withHeader());
        List<CustomerInfo> customerInfoList = parser.getRecords().stream().map(CustomerInfo::new).collect(Collectors.toList());
        parser.close();
        return customerInfoList;
    }

    public static List<Country> getCountryList() throws IOException {
        String txtFileName = "CountryCodeMapping.csv";
        CSVParser parser = new CSVParser(new FileReader(txtFileName), CSVFormat.DEFAULT.withHeader());
        List<Country> countries = parser.getRecords().stream().map(Country::new).collect(Collectors.toList());
        parser.close();
        return countries;
    }
}

