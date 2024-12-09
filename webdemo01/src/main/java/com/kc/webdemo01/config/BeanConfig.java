package com.kc.webdemo01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/9 下午1:44
 */
@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
