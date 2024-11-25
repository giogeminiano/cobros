package com.sms.walmart.cobros.jobConfiguration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
public class JobInit {

    public void jobOffline(){
        try {
            JobWalmart cobros = new JobWalmart();
            cobros.executeCargaOffline();
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("error en la ejecución del job de cobros");
        }
    }


}
