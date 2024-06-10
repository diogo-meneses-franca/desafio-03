package com.pbcompass.apifuncionarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioRespostaDto {

    @NotNull(message = "Id não deve ser nulo")
    private Long id;

    @NotBlank(message = "Nome não deve estar em branco ou nulo")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;

    @Size(min = 11, max = 11, message = "CPF deve possui somente números, tamanho minimo e maximo 11 caracteres")
    @CPF
    private String cpf;

    @NotBlank(message = "Endereço não deve estar em branco ou nulo")
    @Size(max = 150, message = "Endereço deve possui no máximo 150 caracteres")
    private String endereco;

    @NotBlank(message = "Telefone não deve estar em branco ou nulo")
    @Size(min = 10, max = 14, message = "Telefone deve possuir entre 10 e 14 caracteres")
    private String telefone;

    @NotBlank(message = "Email não deve estar em branco ou nulo")
    private String email;

}