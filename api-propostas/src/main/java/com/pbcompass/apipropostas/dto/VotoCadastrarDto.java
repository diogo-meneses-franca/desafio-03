package com.pbcompass.apipropostas.dto;

import com.pbcompass.apipropostas.entities.Voto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoCadastrarDto {

    @NotNull
    private Long funcionarioId;

    @NotNull
    private Long propostaId;

    @NotBlank
    @Pattern(regexp = "APROVAR|REJEITAR", message = "Valor inválido. Use APROVAR ou REJEITAR.")
    private Voto.Decisao decisao;

    public Voto toVoto(){
        Voto voto = new Voto();
        voto.setFuncionarioId(funcionarioId);
        voto.setDecisao(decisao);
        return voto;
    }
}
