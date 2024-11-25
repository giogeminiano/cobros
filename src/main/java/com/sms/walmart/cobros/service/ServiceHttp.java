package com.sms.walmart.cobros.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ServiceHttp {

    public Map sendHttpSMS(String url, String token, String params){
        Map map = new HashMap();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Gson gson = new Gson();
        HttpPost postToken = new HttpPost(url);
        log.info("url"+url);
        log.info("token>>>>>"+token);
        log.info("paramas"+params);
        try{

            StringEntity postingString = new StringEntity(params, "UTF-8");
            postToken.setEntity(postingString);
            postToken.setHeader("Content-Type", "application/json");
            postToken.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+token);
            HttpResponse response = (HttpResponse) httpclient.execute(postToken);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                if(!Objects.isNull(response.getEntity())){
                    log.info("response"+response.getEntity());
                    HttpEntity entity2 = response.getEntity();
                    map = gson.fromJson(EntityUtils.toString(entity2), Map.class);
                }else{
                    map.put("message","service not found");
                    map.put("code",407);
                    map.put("success" ,false);
                }
            }else{
                log.info("response:::::"+response);
                map.put("message","Error bad request");
                map.put("code",408);
                map.put("success" ,false);
            }
        }catch (Exception ex){
            map.put("message","invalid response this petition , review please");
            map.put("code",409);
            map.put("success" ,false);
            log.info("error HTTPS"+ex.getStackTrace());
        }
        return  map;
    }
}