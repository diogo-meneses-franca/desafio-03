package com.pbcompass.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/funcionarios/**").uri("lb://ms-funcionario"))
                .route(r -> r.path("/api/v1/propostas/**").uri("lb://ms-propostas"))
                .route(r -> r.path("/api/v1/resultados/**").uri("lb://ms-resultados"))
                .build();
    }

}
