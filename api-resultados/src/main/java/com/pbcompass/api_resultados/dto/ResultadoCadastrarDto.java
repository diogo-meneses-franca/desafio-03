package com.pbcompass.api_resultados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ResultadoCadastrarDto {

    @NotNull(message =  "O id da proposta não deve ser nulo")
    private Long propostaid;

    @NotBlank(message = "Resultado nao deve ser nulo ou estar em branco")
    @Pattern(regexp = "APROVAR|REJEITAR", message = "Valor inválido. Use APROVAR ou REJEITAR.")
    private String resultado;
}
