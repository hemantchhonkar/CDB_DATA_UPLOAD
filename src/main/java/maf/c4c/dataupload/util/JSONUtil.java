package maf.c4c.dataupload.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import maf.c4c.dataupload.model.CustomerInfo;

import java.util.List;
import java.util.stream.Collectors;

public class JSONUtil {

    public static List<String> mapToJsonList(List<CustomerInfo> customerInfoList) throws JsonProcessingException {
        return customerInfoList.stream().map(JSONUtil::toJsonString).collect(Collectors.toList());
    }

    public static String toJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
