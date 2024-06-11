package com.pbcompass.apipropostas.exception;

import com.pbcompass.apipropostas.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.Objects;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErroPadrao> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(CriadorUnicoException.class)
    public ResponseEntity<MensagemErroPadrao> criadorUnicoException(CriadorUnicoException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        "O criador da proposta não pode ser alterado",
                        request.getRequestURI()));
    }

    @ExceptionHandler(ErroAoBuscarFuncionarioException.class)
    public ResponseEntity<MensagemErroPadrao> erroAoBuscarFuncionarioException(ErroAoBuscarFuncionarioException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<MensagemErroPadrao> recursoNãoEncontrado(RecursoNaoEncontrado e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(VotoInvalidoException.class)
    public ResponseEntity<MensagemErroPadrao> votoUnicoException(VotoInvalidoException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(VotacaoEmAndamentoException.class)
    public ResponseEntity<MensagemErroPadrao> votacaoEmAndamentoException(VotacaoEmAndamentoException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(FuncionarioNaoAutorizadoException.class)
    public ResponseEntity<MensagemErroPadrao> funcionarioNaoAutorizadoException(FuncionarioNaoAutorizadoException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new MensagemErroPadrao(
                        new Date(),
                        HttpStatus.FORBIDDEN.value(),
                        e.getMessage(),
                        request.getRequestURI()));
    }
}
