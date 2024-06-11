package com.pbcompass.api_resultados.controller.v1;

import com.pbcompass.api_resultados.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResultadoControllerImpl implements ResultadoController {

    private final ResultadoService service;
}
