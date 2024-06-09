package com.pbcompass.apipropostas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioRespostaDto {

    private Long id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;
}
