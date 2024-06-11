package com.pbcompass.apiresultados.controller.v1;

import com.pbcompass.apiresultados.dto.ResultadoRespostaDto;
import com.pbcompass.apiresultados.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResultadoControllerImpl implements ResultadoController {

    private final ResultadoService service;

    @Override
    public ResponseEntity<ResultadoRespostaDto> buscarPorId(Long id) {
        ResultadoRespostaDto resultado = service.buscarPorId(id);
        return ResponseEntity.ok(resultado);
    }

    @Override
    public ResponseEntity<Page<ResultadoRespostaDto>> buscarTodos(
            Integer page,
            Integer size,
            String direction) {
        return ResponseEntity.ok(service.buscarTodos(page, size, direction));
    }
}
