package com.pbcompass.apipropostas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropostaCadastrarDto {

    @NotBlank(message = "Nome da proposta não deve ser nulo ou estar em branco")
    @Size(min = 10, max = 150, message = "Nome da proposta deve ter no minimo 10 e no maximo 150 caracteres")
    private String nome;

    @NotBlank(message = "Descrição nao deve ser nulo ou estar em branco")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String descricao;

    private Integer duracaoEmMinutos;

    private Date inicioVotacao;
}
