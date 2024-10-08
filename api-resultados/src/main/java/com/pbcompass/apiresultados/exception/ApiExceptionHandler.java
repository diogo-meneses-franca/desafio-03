package com.pbcompass.apiresultados.exception;

import com.pbcompass.apiresultados.exception.custom.ErroAoBuscarPropostaException;
import com.pbcompass.apiresultados.exception.custom.MensagemErroPadrao;
import com.pbcompass.apiresultados.exception.custom.RecursoNaoEncontrado;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(ErroAoBuscarPropostaException.class)
    public ResponseEntity<MensagemErroPadrao> erroAoBuscarProposta(ErroAoBuscarPropostaException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<MensagemErroPadrao> recursoNaoEncontrado(RecursoNaoEncontrado e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroPadrao> dadosInseridosInvalidos(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Dados inseridos inválidos",
                        request.getRequestURI()));
    }
}
