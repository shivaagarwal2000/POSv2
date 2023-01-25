package com.increff.employee.client;

import com.google.gson.Gson;
import com.increff.employee.model.data.CommonOrderItemData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class OrderClient {

    //TODO: move the url to properties file
    public static final String completeUrl = "http://localhost:9500/invoice/api/invoice";
    public String getInvoice(List<CommonOrderItemData> commonOrderItemDatas) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(completeUrl);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        StringEntity stringEntity = new StringEntity(new Gson().toJson(commonOrderItemDatas));
        httpPost.setEntity(stringEntity);
        System.out.println("executing request: " + httpPost.getRequestLine()); //TODO: refactor: clean up

        HttpResponse httpResponse = httpClient.execute(httpPost);
//        System.out.println(httpResponse);
//        HttpEntity responseHttpEntity = httpResponse.getEntity();
//        InputStream content = responseHttpEntity.getContent();
//        System.out.println();
        return EntityUtils.toString(httpResponse.getEntity());
    }
}
