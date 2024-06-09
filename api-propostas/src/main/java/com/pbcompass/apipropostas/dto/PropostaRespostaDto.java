package com.pbcompass.apipropostas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropostaRespostaDto {

    private Long id;
    private String nome;
    private String descricao;
    private FuncionarioRespostaDto criador;
    private Integer duracaoEmMinutos;
    private Date inicioVotacao;
}
