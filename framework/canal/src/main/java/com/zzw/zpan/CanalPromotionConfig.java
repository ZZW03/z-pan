package com.zzw.zpan;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Configuration
@Data
@ConfigurationProperties(prefix = "com.zzw.pan.canal")
@Slf4j
public class CanalPromotionConfig {

    private String canalServerIp ;

    private int canalServerPort ;

    private String userName ;

    private String password;

    private String destination ;


    @Bean("promotionConnector")
    public CanalConnector newSingleConnector(){

        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalServerIp,
                canalServerPort), destination, userName, password);
        if (canalConnector.checkValid()){
            log.info("canal init success");
        }

        return canalConnector;
    }

}
