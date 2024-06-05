package com.pbcompass.apifuncionarios.exception;

import com.pbcompass.apifuncionarios.exception.custom.ErroAoSalvarFuncionario;
import com.pbcompass.apifuncionarios.exception.custom.RecursoNaoEncontrado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(ErroAoSalvarFuncionario.class)
    public ResponseEntity<String> erroAoSalvarFuncionario(ErroAoSalvarFuncionario e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ResponseEntity<String> recursoNaoEncontrado(RecursoNaoEncontrado e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
