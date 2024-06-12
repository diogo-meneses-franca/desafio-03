package com.pbcompass.apipropostas.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoCadastrarDto {

    private Long propostaId;
    private String resultado;

}
