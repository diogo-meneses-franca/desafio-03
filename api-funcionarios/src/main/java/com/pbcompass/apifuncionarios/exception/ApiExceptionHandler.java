package com.pbcompass.apifuncionarios.exception;

import com.pbcompass.apifuncionarios.exception.custom.AtualizacaoNaoPermitida;
import com.pbcompass.apifuncionarios.exception.custom.DadosUnicosException;
import com.pbcompass.apifuncionarios.exception.custom.MensagemErroPadrao;
import com.pbcompass.apifuncionarios.exception.custom.RecursoNaoEncontrado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MensagemErroPadrao> entityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(DadosUnicosException.class)
    public ResponseEntity<MensagemErroPadrao> dadosUnicosException(DadosUnicosException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroPadrao> dadosDeEntradaInvalidosException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<MensagemErroPadrao> recursoNaoEncontrado(RecursoNaoEncontrado e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(AtualizacaoNaoPermitida.class)
    public ResponseEntity<MensagemErroPadrao> atualizacaoNaoPermitida(AtualizacaoNaoPermitida e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.FORBIDDEN.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<MensagemErroPadrao> noResourceFoundException(NoResourceFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MensagemErroPadrao> mensagemDeErroPadrao(RuntimeException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                        request.getRequestURI()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<MensagemErroPadrao> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        "Método não permitido",
                        request.getRequestURI()));
    }
}
