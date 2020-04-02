package maf.c4c.dataupload.service;

import maf.c4c.dataupload.model.Country;
import maf.c4c.dataupload.util.CSVUtil;
import maf.c4c.dataupload.util.JSONUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountryService {
    private static Map<String, Country> countryMap;
    public static void main(String[] args) throws IOException {
       // System.out.println(CountryService.getCountryCodeByISO("NZL"));
    }
    static {
        List<Country> countries = null;
        try {
            countries = CSVUtil.getCountryList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        countryMap = countries.stream().collect(Collectors.toMap(Country::getISO,Country::getCountry ));
        System.out.println(countryMap);

    }

    public String getCountryCodeByISO(String countryISO) {
        if(countryISO.length() >0 ) {
            return countryMap.get(countryISO).getCode();
        } else {
            return countryISO;
        }
    }
}
