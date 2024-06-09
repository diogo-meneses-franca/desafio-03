package com.pbcompass.apipropostas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiPropostasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiPropostasApplication.class, args);
    }

}
