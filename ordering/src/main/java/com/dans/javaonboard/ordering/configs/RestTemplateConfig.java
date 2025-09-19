package com.dans.javaonboard.ordering.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplateOutBound(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri(System.getenv("HOST_NAME_OUTBOUND"))
                .basicAuthentication(
                        System.getenv("BASIC_USERNAME_OUTBOUND"),
                        System.getenv("BASIC_PASSWORD_OUTBOUND")
                )
                .build();
    }
}
