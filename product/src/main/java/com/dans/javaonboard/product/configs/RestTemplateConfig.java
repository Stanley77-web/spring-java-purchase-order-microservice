package com.dans.javaonboard.product.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplateOutBound(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri("https://dev6.dansmultipro.com/java/dans/onboard/java/")
                .basicAuthentication(
                        System.getenv("BASIC_USERNAME_OUTBOUND"),
                        System.getenv("BASIC_PASSWORD_OUTBOUND")
                )
                .build();
    }
}
