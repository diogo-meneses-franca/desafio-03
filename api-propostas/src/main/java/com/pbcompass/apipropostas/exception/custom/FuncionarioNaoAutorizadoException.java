package com.pbcompass.apipropostas.exception.custom;

public class FuncionarioNaoAutorizadoException extends RuntimeException {
    public FuncionarioNaoAutorizadoException(String mensagem) {
        super(mensagem);
    }
}
