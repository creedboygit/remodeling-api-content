package com.hanssem.remodeling.content;

import com.hanssem.remodeling.content.common.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableFeignClients
@EnableJpaAuditing
@ServletComponentScan
@EnableConfigurationProperties(WebConfig.class)
public class ApiContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiContentApplication.class, args);
    }

}
