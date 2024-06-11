package com.pbcompass.apiresultados.controller.v1;

import com.pbcompass.apiresultados.dto.PropostaRespostaDto;
import com.pbcompass.apiresultados.dto.ResultadoRespostaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/resultados")
public interface ResultadoController {

    @GetMapping("/{id}")
    ResponseEntity<ResultadoRespostaDto> buscarPorId(@PathVariable Long id);

}
