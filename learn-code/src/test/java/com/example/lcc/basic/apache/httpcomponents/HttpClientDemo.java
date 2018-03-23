package com.example.lcc.basic.apache.httpcomponents;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.Result;

/**
 * @date 2018/3/23
 */
public class HttpClientDemo {

    @Test
    public void basic() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.baidu.com");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            String toString = EntityUtils.toString(entity1,"utf-8");
            System.out.println(toString);
            EntityUtils.consume(entity1);
        } finally {
            response1.close();
        }

    }
}
