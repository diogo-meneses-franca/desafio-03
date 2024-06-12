package com.pbcompass.apipropostas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Duração da votação em minutos", example = "60", required = false, defaultValue = "1")
    private Integer duracaoEmMinutos;

    @Schema(description = "Data e hora de início da votação", example = "2023-06-12T10:00:00Z", required = false)
    private Date inicioVotacao;
}
