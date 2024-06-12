package com.pbcompass.apiresultados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiResultadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiResultadosApplication.class, args);
	}

}
