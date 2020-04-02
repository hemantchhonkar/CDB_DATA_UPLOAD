package maf.c4c.dataupload.service;

import maf.c4c.dataupload.model.CustomerInfo;
import maf.c4c.dataupload.util.CSVUtil;
import maf.c4c.dataupload.util.HTTPRequestUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.*;
import java.util.List;
import java.util.concurrent.Future;

public class CustomerService {
    private static long uploadCount = 0;
    private static long failedCount = 0;

        public static void process(CustomerInfo customerInfo) {
            System.out.println("CustomerService process started....");
            final CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
            final CookieStore cookieStore = new BasicCookieStore();
            final HttpClientContext localContext = HttpClientContext.create();
            localContext.setCookieStore(cookieStore);
            httpClient.start();
            getCSRFTokenAndPerformAPIOperations(customerInfo, httpClient, localContext);

        }

    private static void getCSRFTokenAndPerformAPIOperations(CustomerInfo customerInfo, CloseableHttpAsyncClient httpClient, HttpClientContext localContext) {
        final HttpGet get = HTTPRequestUtil.createGetCSRFTokenRequest();
        System.out.println("Going to get CSRF token....");
        Future<HttpResponse> responseGet = httpClient.execute(get, localContext, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse response2) {
                System.out.println(get.getRequestLine() + "->" + response2.getStatusLine());
                Header[] header = response2.getHeaders("X-CSRF-Token");
                String csrfToken = header[0].getValue();
                createCustomer(csrfToken, customerInfo, httpClient, localContext);
//                getCustomerByEmail(csrfToken,
//                        customerInfo,
//                        httpClient,
//                        localContext);
            }

            @Override
            public void failed(Exception e) {

            }
            @Override
            public void cancelled() {

            }
        });
    }

    private static void getCustomerByEmail(String csrfToken,
                                           CustomerInfo customerInfo,
                                           CloseableHttpAsyncClient httpClient,
                                           HttpClientContext localContext) {
        final HttpGet get = HTTPRequestUtil.createGetCustomerRequest(csrfToken,
                customerInfo.getEmail());

        Future<HttpResponse> responseGet = httpClient.execute(get, localContext, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse response2) {
                try {
                    String responseString = new BasicResponseHandler().handleResponse(response2);
                    String lookUpField = "\"ObjectID\"";
                    if(responseString.contains(lookUpField)){
                        System.out.println("Customer found for email "+customerInfo.getEmail());
                        String ObjectID = getObjectId(responseString, lookUpField);
                        System.out.println("Object ID "+ObjectID);
                        patchCustomer(ObjectID, csrfToken, customerInfo, httpClient, localContext);
                    } else {
                        System.out.println("Customer not found for email "+customerInfo.getEmail());
                        createCustomer(csrfToken, customerInfo, httpClient, localContext);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failed(Exception e) {

            }
            @Override
            public void cancelled() {

            }
        });
    }

    private static String getObjectId(String responseString, String lookUpField) {
        int fromOffSet = responseString.indexOf(lookUpField)+lookUpField.length()+2;
        int toOffset = fromOffSet+responseString.substring(fromOffSet).indexOf("\"");
        return responseString.substring(fromOffSet, toOffset);
    }

    private static void patchCustomer(String objectID, String csrfToken, CustomerInfo customerInfo, CloseableHttpAsyncClient httpClient, HttpClientContext localContext) throws IOException {
        HttpPatch httpPatch = HTTPRequestUtil.createPATCHCustomer(csrfToken, objectID, customerInfo);
        Future<HttpResponse> response = httpClient.execute(httpPatch, localContext, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                System.out.println("Customer Patched " + customerInfo);
            }
            @Override
            public void failed(Exception e) {
                System.out.println("Customer PATCH Failed" + customerInfo);
                e.printStackTrace();
            }
            @Override
            public void cancelled() {
                System.out.println("Customer PATCH Cancelled" + customerInfo);
            }
        });
    }

    private static void createCustomer(String csrfToken, CustomerInfo customerInfo, CloseableHttpAsyncClient httpClient, HttpClientContext localContext) {
        try {
            final HttpPost post = HTTPRequestUtil.createPOSTCreateCustomer(csrfToken, customerInfo);
            Future<HttpResponse> response = httpClient.execute(post, localContext, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse httpResponse) {
                    System.out.println("Customer Created { Count - "+(++uploadCount)+"}" + customerInfo);
                }
                @Override
                public void failed(Exception e) {
                    System.out.println("Customer POST Failed { Count- "+(++failedCount)+"}"+ customerInfo);
                            e.printStackTrace();
                }
                @Override
                public void cancelled() {
                    System.out.println("Customer POST cancelled" + customerInfo);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
