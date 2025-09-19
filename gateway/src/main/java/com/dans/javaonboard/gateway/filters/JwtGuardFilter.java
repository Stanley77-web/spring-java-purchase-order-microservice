package com.dans.javaonboard.gateway.filters;

import com.dans.javaonboard.gateway.dtos.VerifyTokenDto;
import com.dans.javaonboard.gateway.services.EncryptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGuardFilter implements GlobalFilter, Ordered {

    private final WebClient authenticationClient;
    private final EncryptionService encryptionService;

    public JwtGuardFilter(WebClient authenticationClient, EncryptionService encryptionService) {
        this.authenticationClient = authenticationClient;
        this.encryptionService = encryptionService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();

        System.out.println(path);

        if (isPublicEndpoint(path)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println(token);

        return authenticationClient
                .post()
                .uri("/v1/verify")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(VerifyTokenDto.class)
                .flatMap(
                        response -> {
                            System.out.println("JWT Guard Filter: " + response.getUserDto().getUsername());
                            System.out.println("JWT Guard Filter: " + exchange);
                            if (response.getMessage() == null) {
                                return this.handleUnauthorized(exchange);
                            }

                            try {
                                ObjectMapper mapper = new ObjectMapper();

                                String userJson = mapper.writeValueAsString(response.getUserDto());
                                String encryptedUser = this.encryptionService.encrypt(userJson);

                                ServerWebExchange mutatedExchange = exchange.mutate()
                                        .request(exchange.getRequest().mutate()
                                                .header("x-user", encryptedUser)
                                                .build())
                                        .build();

                                return chain.filter(mutatedExchange);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .onErrorResume( t -> {
                    System.out.println("JWT Guard Filter Error: " + t.getMessage());
                    return this.handleUnauthorized(exchange);
                }).then();
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
        String responseBody = "{\"error\": \"Unauthorized\"}";
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(responseBody.getBytes())));
    }

    private boolean isPublicEndpoint(String path) {
        return
                path.contains("/authentication/v1/login") ||
                path.contains("/authentication/v1/register");
    }
}