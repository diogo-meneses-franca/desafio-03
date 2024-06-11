package com.pbcompass.api_resultados.exception;

import com.pbcompass.api_resultados.exception.custom.MensagemErroPadrao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MensagemErroPadrao> runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Ocorreu um erro inesperado, tente novamente mais tarde!",
                        request.getRequestURI()));
    }
}
