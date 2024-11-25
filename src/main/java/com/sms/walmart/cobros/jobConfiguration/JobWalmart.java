package com.sms.walmart.cobros.jobConfiguration;

import com.google.gson.Gson;
import com.sms.walmart.cobros.consql.AfiliadoWalmart;
import com.sms.walmart.cobros.consql.ConnectionDBCobro;
import com.sms.walmart.cobros.consql.ConnectionDBSise;
import com.sms.walmart.cobros.service.ServiceHttp;
import com.sms.walmart.cobros.utils.UtilsConstans;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jobrunr.jobs.annotations.Job;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class JobWalmart {

    @Job(name = "job carga offline")
    @Transactional
    public void executeCargaOffline() {
        ConnectionDBCobro cobros = new ConnectionDBCobro();
        ConnectionDBSise sise = new ConnectionDBSise();
        List<Integer> current = new ArrayList<>();
        List<Integer> currentN = new ArrayList<>();
        try{
            Map<Integer,String> clafils = cobros.getCobroData("fecha");
            filterData(clafils,current,currentN);
            log.info(current.toString());
            log.info(currentN.toString());
            if(!current.isEmpty()) {
                List<AfiliadoWalmart> cobroCurrent = sise.getSiseData(current);
                sendMessage(cobroCurrent,UtilsConstans.COBRO_MESSAGE_SUCCESS);
            }
            if(!currentN.isEmpty()){
                List<AfiliadoWalmart> cobroCurrentN = sise.getSiseData(currentN);
                sendMessage(cobroCurrentN,UtilsConstans.COBRO_MESSAGE_FAIL);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            log.info("Se concluye el envio de mensajes");
        }
    }

    private void filterData(Map<Integer,String> map,List<Integer> current,List<Integer> currentN){
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if(entry.getValue().equalsIgnoreCase("S"))
                current.add(entry.getKey());
            else
                currentN.add(entry.getKey());
        }
    }


    void sendMessage(List<AfiliadoWalmart> phonelist,String message){
        List<String> phones = new ArrayList<>();
        try{
            if(!phonelist.isEmpty()){
                phonelist.stream().forEach(phone->{
                    phones.add(phone.getPhone().trim());
                });
                ServiceHttp httpservice = new ServiceHttp();
                //Map response = httpservice.sendHttpSMS(UtilsConstans.URL_DIRECTO_BULK, UtilsConstans.URL_DIRECTO_TOKEN
                  //      , new Gson().toJson(createListPhone(phones,message)));
               // log.info("response>>>>>"+response);
            }else{
                log.info("phones list is empty  >>>>>>>>>");
            }
        }catch (Exception ex){
            log.info("error in send");
        }
    }


    public List<Map> createListPhone(List<String> phones,String message){
        List<Map> request =  new ArrayList<>();
        for(String tmp: phones){
            Map body = new HashMap();
            body.put("from","WALMART");
            body.put("to","52"+tmp);
            body.put("message",message);
            request.add(body);
        }
        System.out.println(request);
        return request;
    }


    public URIBuilder sendMessageSMSOffline(AfiliadoWalmart afiliado){
        URIBuilder builder = null;
        try{
            builder = new URIBuilder("url_api_sms");
            builder.setParameter("from", "Walmart")
                    .setParameter("acc_id","839");

            String account="";
            log.info(" account>>>"+account);
            builder.setParameter("to", "52"+afiliado.getPhone());
            builder.setParameter("message", UtilsConstans.ACTIVE_MESSAGE.replace("@@",afiliado.getFinfecha().toString().substring(0,10)));
        }catch (Exception ex){
            log.info("ex error"+ex.getMessage());
        }
        return  builder;
    }

    public Map sendHttpSMSOffline(URIBuilder builder,String token){
        Map map = new HashMap();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(builder.toString());
        Gson gson = new Gson();
        try{
            HttpPost postToken = new HttpPost(builder.build());
            postToken.setHeader("Content-Type", "application/json");
            postToken.setHeader("Authorization","Bearer "+token);
            HttpResponse response = (HttpResponse) httpclient.execute(postToken);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                System.out.println("validatePolicy response ===>"+response.getEntity().toString());
                if(!Objects.isNull(response.getEntity())){
                    System.out.println("response"+response.getEntity());
                    HttpEntity entity2 = response.getEntity();
                    String tmp = EntityUtils.toString(entity2);
                    System.out.println(tmp);
                    map.put("response",tmp);
                }else{
                    map.put("message","service not found");
                    map.put("code",407);
                    map.put("success" ,false);
                }
            }else{
                System.out.println("response:::::"+response);
                map.put("message","Error bad request");
                map.put("code",408);
                map.put("success" ,false);
            }
        }catch (Exception ex){
            map.put("message","invalid response this petition , review please");
            map.put("code",409);
            map.put("success" ,false);
            System.out.println("error HTTPS"+ex.getMessage());
        }
        return  map;
    }


}