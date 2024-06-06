package com.pbcompass.apifuncionarios.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MensagemErroPadrao> entityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new MensagemErroPadrao(
                        System.currentTimeMillis(),
                        HttpStatus.NOT_FOUND.value(),
                        "Not found",
                        e.getMessage(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(CpfUnicoException.class)
    public ResponseEntity<MensagemErroPadrao> cpfUnicoException(CpfUnicoException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new MensagemErroPadrao(
                        System.currentTimeMillis(),
                        409,
                        "Erro ao cadastrar funcionário",
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroPadrao> dadosDeEntradaInvalidosException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new MensagemErroPadrao(
                        System.currentTimeMillis(),
                        422,
                        "Erro ao cadastrar funcionário",
                        "Dados de entrada inválidos",
                        request.getRequestURI()));
    }

}
