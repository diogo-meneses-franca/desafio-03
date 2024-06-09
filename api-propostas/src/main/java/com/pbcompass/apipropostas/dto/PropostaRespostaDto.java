package com.pbcompass.apipropostas.dto;

import java.util.Date;

public class PropostaRespostaDto {

    private Long id;
    private String nome;
    private String descricao;
    private FuncionarioRespostaDto criador;
    private Integer duracaoEmMinutos;
    private Date inicioVotacao;
}
