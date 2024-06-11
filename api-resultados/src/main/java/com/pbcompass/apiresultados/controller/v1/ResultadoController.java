package com.pbcompass.apiresultados.controller.v1;

import com.pbcompass.apiresultados.dto.ResultadoRespostaDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/resultados")
public interface ResultadoController {

    @GetMapping("/{id}")
    ResponseEntity<ResultadoRespostaDto> buscarPorId(@PathVariable Long id);

    @GetMapping
    ResponseEntity<Page<ResultadoRespostaDto>> buscarTodos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

}
