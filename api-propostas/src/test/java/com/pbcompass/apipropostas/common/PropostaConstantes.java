package com.pbcompass.apipropostas.common;

import com.pbcompass.apipropostas.entities.Funcionario;
import com.pbcompass.apipropostas.entities.Proposta;

import java.util.ArrayList;
import java.util.Date;

public class PropostaConstantes {

  public static final Proposta PROPOSTA = new Proposta(
          1L,
          "PropostaNome",
          "descrição da proposta",
          new Funcionario(),
          1,
          new Date(),
          new ArrayList<>());

  public static final Proposta PROPOSTA_INVALIDA = new Proposta(null, "", "", null, null, null, null);

}
