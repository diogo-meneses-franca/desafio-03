package com.pbcompass.apifuncionarios.dto;
import jakarta.validation.constraints.NotBlank;
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
public class FuncionarioCadastrarDto {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

    @NotBlank
    @Size(max = 200)
    private String endereco;

    @NotBlank
    @Size(max = 14)
    private String telefone;

    @NotBlank
    private String email;

}
