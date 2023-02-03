package com.pos.client;

import com.google.gson.Gson;
import com.pos.model.data.CommonOrderItemData;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class InvoiceClient {

    //TODO: move the url to properties file
    public static final String completeUrl = "http://localhost:9500/invoice/api/invoice";// TODO final variables should be uppercase Priority: 5
    public String getInvoice(List<CommonOrderItemData> commonOrderItemDatas) throws IOException {// TODO Catch and throw ApiException Priority: 5

        // TODO Use rest template Priority: 5
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(completeUrl);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        // TODO: try catch and return -- no other exception other than ApiException
        StringEntity stringEntity = new StringEntity(new Gson().toJson(commonOrderItemDatas));
        httpPost.setEntity(stringEntity);
        System.out.println("executing request: " + httpPost.getRequestLine()); //TODO: refactor: clean up

        HttpResponse httpResponse = httpClient.execute(httpPost);
        return EntityUtils.toString(httpResponse.getEntity());
    }
}
