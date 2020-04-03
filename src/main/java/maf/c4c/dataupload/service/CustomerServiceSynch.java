package maf.c4c.dataupload.service;

import maf.c4c.dataupload.model.CustomerInfo;
import maf.c4c.dataupload.thread.Processors;
import maf.c4c.dataupload.util.HTTPRequestUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CustomerServiceSynch {
    final static Logger logger = Logger.getLogger(CustomerServiceSynch.class);
    private static long uploadCount = 0;
    private static long failedCount = 0;

        public static void process(CustomerInfo customerInfo) {
           logger.info("CustomerService process started....");
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final CookieStore cookieStore = new BasicCookieStore();
            final HttpClientContext localContext = HttpClientContext.create();
            localContext.setCookieStore(cookieStore);
            getCSRFTokenAndPerformAPIOperations(customerInfo, httpClient, localContext);

        }

    private static void getCSRFTokenAndPerformAPIOperations(CustomerInfo customerInfo, HttpClient httpClient, HttpClientContext localContext) {
        final HttpGet get = HTTPRequestUtil.createGetCSRFTokenRequest();
        logger.info("Going to get CSRF token....");
        try {
            HttpResponse httpResponse = httpClient.execute(get, localContext);
            System.out.println(get.getRequestLine() + "->" + httpResponse.getStatusLine());
            Header[] header = httpResponse.getHeaders("X-CSRF-Token");
            String csrfToken = header[0].getValue();
            logger.info("CSRF token...."+csrfToken);
            createCustomer(csrfToken, customerInfo, httpClient, localContext);
        }
        catch ( Exception e){
            logger.error("CSRF token....Failed { Count - " + (++failedCount) + "}" + customerInfo, e);
            writeFailedRecordsIntoAFile(customerInfo);
            e.printStackTrace();
        }
    }

    private static String getObjectId(String responseString, String lookUpField) {
        int fromOffSet = responseString.indexOf(lookUpField)+lookUpField.length()+2;
        int toOffset = fromOffSet+responseString.substring(fromOffSet).indexOf("\"");
        return responseString.substring(fromOffSet, toOffset);
    }

    private static void createCustomer(String csrfToken, CustomerInfo customerInfo, HttpClient httpClient, HttpClientContext localContext) {
        try {
            final HttpPost post = HTTPRequestUtil.createPOSTCreateCustomer(csrfToken, customerInfo);
            HttpResponse response = httpClient.execute(post, localContext);
            //System.out.println("Customer Response "+response);
            if(response.getStatusLine().getStatusCode() == 201) {
                logger.info("Customer Created { Count - " + (++uploadCount) + "}" + customerInfo);
            } else {
                logger.info("Customer Failed { Count - " + (++failedCount) + "}" + customerInfo);
                writeFailedRecordsIntoAFile(customerInfo);
            }
        } catch (IOException e) {
            logger.error("Customer Failed { Count - " + (++failedCount) + "}" + customerInfo, e);
            writeFailedRecordsIntoAFile(customerInfo);
        }
    }

    private static void writeFailedRecordsIntoAFile(CustomerInfo customerInfo) {
        try
        {
            FileWriter fw = new FileWriter(new File(Processors.BASE_DIR+"/failed/failed.json"), true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(customerInfo.toString());
            //Closing BufferedWriter Stream
            bw.close();
        } catch (IOException e) {
            logger.error("JSON Write failed ", e);
        }
    }
}
