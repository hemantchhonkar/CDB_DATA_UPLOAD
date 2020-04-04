package maf.c4c.dataupload.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import maf.c4c.dataupload.model.CustomerInfo;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;
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

    public static void setFieldsFromJson(JsonParser parser,
                                   CustomerInfo customerInfo,
                                   JsonToken jsonToken) throws IOException {
        String fieldName =parser.getCurrentName();
        if ("RoleCode".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setRoleCode(parser.getText());  //getting text field
        }
        if ("FirstName".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setFirstName(parser.getText());  //getting text field
        }
        if ("LastName".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setLastName(parser.getText());  //getting text field
        }
        if ("GenderCode".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setGenderCode(parser.getText());  //getting text field
        }
        if ("LanguageCode".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setLanguageCode(parser.getText());  //getting text field
        }
        if ("NationalityCountryCode".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setNationalityCountryCode(parser.getText());  //getting text field
        }
        if ("CountryCode".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setCountryCode(parser.getText());  //getting text field
        }
        if ("Phone".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setPhone(parser.getText());  //getting text field
        }
        if ("Mobile".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setMobile(parser.getText());  //getting text field
        }
        if ("Email".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setEmail("JSON_TEST"+parser.getText());  //getting text field
        }
        if ("NonSAPExternalSystem_KUT".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setNonSAPExternalSystem(parser.getText());  //getting text field
        }
        if ("CUST_EXT_ID_KUT".equals(fieldName)) {
            parser.nextToken();  //next token contains value
            customerInfo.setCustomrExternalId(parser.getText());  //getting text field
        }
    }
}
