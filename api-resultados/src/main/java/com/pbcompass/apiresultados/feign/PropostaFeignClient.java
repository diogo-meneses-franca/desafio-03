package com.pbcompass.apiresultados.feign;

import com.pbcompass.apiresultados.dto.PropostaRespostaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-propostas", url = "http://localhost:8080")
public interface PropostaFeignClient {

    @GetMapping("/api/v1/propostas/{propostaId}")
    ResponseEntity<PropostaRespostaDto> buscarPorId(@PathVariable("propostaId") Long propostaId);

}
