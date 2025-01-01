package com.kc.webdemo01.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/1/1 上午11:03
 */
@Slf4j
@Configuration
public class MyRibbonConfig {
    @Bean
    public IRule myRule() {
        //定义为随机，默认是轮询
        return new RandomRule();
    }
}
