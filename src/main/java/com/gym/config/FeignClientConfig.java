package com.gym.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${internal.api.secret}")
    private String internalSecret;

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {

            System.out.println("ADDING INTERNAL SECRET HEADER");

            requestTemplate.header(
                    "X-Internal-Secret",
                    internalSecret
            );
        };
    }
}
