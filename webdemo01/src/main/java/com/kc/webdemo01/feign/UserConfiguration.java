package com.kc.webdemo01.feign;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Client;
import feign.Request;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/3/7 下午7:05
 */
class UserConfiguration {

//    @Value("${snow.incident.username}")
//    String username;
//    @Value("${snow.incident.password}")
//    String password;

//    @Bean
//    public Client feignClient() {
//        return new Client.Default(SSLUtils.createTrustAnySSLSocketFactory(), TrustAnyHostnameVerifier.INSTANCE);
//    }



//    @Bean
//    public Request.Options feignOptions() {
//        return new Request.Options(Duration.ofSeconds(10), Duration.ofSeconds(58), true);
//    }

//    @Bean
//    RequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor(username, password);
//    }
//
//    @Bean
//    Encoder encoder(ObjectFactory<HttpMessageConverters> messageConverters) {
//        return new SpringEncoder(messageConverters);
//    }

//    @Bean
//    Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters,
//                    ObjectProvider<HttpMessageConverterCustomizer> customizers) {
//        SpringDecoder springDecoder = new SpringDecoder(messageConverters, customizers);
//        return (response, type) -> {
//            JsonNode jsonNode = (JsonNode) springDecoder.decode(response, JsonNode.class);
//            return SNowJsonNodes.of(jsonNode);
//        };
//    }
}