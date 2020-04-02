package maf.c4c.dataupload.model;

import maf.c4c.dataupload.util.JSONUtil;
import org.apache.commons.csv.CSVRecord;

public class Country {
    private String name;
    private String code;
    private String ISO;
    private int codeNumber;


    public Country getCountry(){
        return this;
    }

    public Country(CSVRecord record) {
        this.name = record.get("Country Name");
        this.code = record.get("CountryCode");
        this.ISO = record.get("CountryCodeISO");
        this.codeNumber = Integer.parseInt(record.get("CodeNumeric"));
    }

    public Country(String name, String code, String ISO, int codeNumber) {
        this.name = name;
        this.code = code;
        this.ISO = ISO;
        this.codeNumber = codeNumber;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getISO() {
        return ISO;
    }

    public int getCodeNumber() {
        return codeNumber;
    }

//    @Override
//    public String toString() {
//        return JSONUtil.toJsonString(this);
//    }
}
