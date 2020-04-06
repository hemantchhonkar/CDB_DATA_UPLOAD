package maf.c4c.dataupload.util;

import maf.c4c.dataupload.model.CustomerInfo;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.*;
import static java.nio.charset.StandardCharsets.*;
//https://my350137.crm.ondemand.com
public class HTTPRequestUtil {
    public final static String API_URL = "https://my349203.crm.ondemand.com/sap/c4c/odata/v1/c4codataapi";
    public final static String Basic_Auth = "Basic aGVtYW50OkNhcnJlZm91ckAx";

    public static HttpGet createGetCSRFTokenRequest() {
        final HttpGet get = new HttpGet(API_URL);
        get.setHeader("X-CSRF-Token", "fetch");
        get.setHeader("Authorization", Basic_Auth);
        return get;
    }

    public static HttpGet createGetCustomerRequest(String csrfToken, String email) {
        final HttpGet get = new HttpGet(API_URL+"/IndividualCustomerCollection?Email="+email);
        get.setHeader("X-CSRF-Token", "fetch");
        get.setHeader("Authorization", Basic_Auth);
        get.addHeader("Accept", "application/json");
        return get;
    }
    public static HttpPatch createPATCHCustomer(String csrfToken, String objectID, CustomerInfo customerInfo) throws IOException {
        final HttpPatch patch =
                new HttpPatch(API_URL+"/IndividualCustomerCollection"+"('"+objectID+"')");
        patch.addHeader("Content-Type", "application/json");
        patch.addHeader("Authorization", Basic_Auth);
        patch.addHeader("X-CSRF-Token", csrfToken);
        StringEntity content;
        content = new StringEntity(JSONUtil.toJsonString(customerInfo));
        patch.setEntity(content);
        patch.setEntity(content);
        return patch;
    }

    public static HttpPost createPOSTCreateCustomer(String csrfToken, CustomerInfo customerInfo) throws IOException {
        final HttpPost post = new HttpPost(API_URL+"/IndividualCustomerCollection");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", Basic_Auth);
        post.addHeader("X-CSRF-Token", csrfToken);
        StringEntity content;
        System.out.println("Going to create --- "+JSONUtil.toJsonString(customerInfo));
        content = new StringEntity(JSONUtil.toJsonString(customerInfo), "UTF-8");
        post.setEntity(content);
        post.setEntity(content);
        return post;
    }
}
