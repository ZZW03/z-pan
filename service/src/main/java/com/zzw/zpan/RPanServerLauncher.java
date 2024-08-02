package com.zzw.zpan;


import com.zzw.zpan.constants.RPanConstants;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = RPanConstants.BASE_COMPONENT_SCAN_PATH)
@Slf4j
@MapperScan(basePackages = RPanConstants.BASE_COMPONENT_SCAN_PATH + ".zpan.modules.**.mapper")
@EnableIntegration
@EnableScheduling
public class RPanServerLauncher {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RPanServerLauncher.class);
    }

}
