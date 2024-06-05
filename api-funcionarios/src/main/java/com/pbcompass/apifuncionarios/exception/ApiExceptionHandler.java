package com.pbcompass.apifuncionarios.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(CpfUnicoException.class)
    public ResponseEntity<MensagemErroPadrao> cpfUnicoException(CpfUnicoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensagemErroPadrao(System.currentTimeMillis(), 409,
                "Erro ao cadastrar funcionário", ex.getMessage(), request.getRequestURI()));
    }

}
