package com.dans.javaonboard.gateway.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestTemplateConfig {
    @Bean
    public WebClient authenticationClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:5001/authentication")
                .build();
    }
}
