package com.pbcompass.apipropostas.feign;

import com.pbcompass.apipropostas.dto.FuncionarioRespostaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-funcionarios", url = "http://localhost:8080")
public interface FuncionarioFeignClient {

    @GetMapping("/api/v1/funcionarios/{id}")
    ResponseEntity<FuncionarioRespostaDto> buscarPorId(@PathVariable("id") Long id);
}
