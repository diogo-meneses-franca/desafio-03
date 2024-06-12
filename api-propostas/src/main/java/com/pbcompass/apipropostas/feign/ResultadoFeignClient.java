package com.pbcompass.apipropostas.feign;

import com.pbcompass.apipropostas.dto.ResultadoCadastrarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-resultados", url = "http://localhost:8080/api/v1/resultados")
public interface ResultadoFeignClient {

    @PostMapping
    ResponseEntity<Void> cadastrar(@RequestBody ResultadoCadastrarDto dto);
}
