package com.pbcompass.apifuncionarios.constantes;

import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.entities.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioConstantes {

    public static final Funcionario FUNCIONARIO = new Funcionario(
            1L,
            "Funcionario Teste",
            "84319254007",
            "Rua Teste",
            "43999887766",
            "teste@email.com"
    );

    public static final List<Funcionario> FUNCIONARIO_LIST = new ArrayList<>();

    public static final FuncionarioCadastrarDto FUNC_CADASTRAR_DTO = new FuncionarioCadastrarDto(
            "Funcionario Teste",
            "84319254007",
            "Rua Teste",
            "43999887766",
            "teste@email.com"
    );

}
