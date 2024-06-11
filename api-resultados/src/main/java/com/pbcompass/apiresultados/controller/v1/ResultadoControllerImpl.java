package com.pbcompass.apiresultados.controller.v1;

import com.pbcompass.apiresultados.service.ResultadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResultadoControllerImpl implements ResultadoController {

    private final ResultadoService service;
}
