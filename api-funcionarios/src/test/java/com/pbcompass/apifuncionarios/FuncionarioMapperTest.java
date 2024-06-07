package com.pbcompass.apifuncionarios;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.mapper.FuncionarioMapper;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.pbcompass.apifuncionarios.constantes.FuncionarioConstantes.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FuncionarioMapperTest {

    @Test
    public void toDto_RetornarFuncionarioRespostaDto() {

        FuncionarioRespostaDto testeDto = FuncionarioMapper.toDto(FUNCIONARIO, FuncionarioRespostaDto.class);

        assertThat(testeDto.getId()).isEqualTo(FUNCIONARIO.getId());
        assertThat(testeDto.getNome()).isEqualTo(FUNCIONARIO.getNome());
        assertThat(testeDto.getCpf()).isEqualTo(FUNCIONARIO.getCpf());
        assertThat(testeDto.getEndereco()).isEqualTo(FUNCIONARIO.getEndereco());
        assertThat(testeDto.getTelefone()).isEqualTo(FUNCIONARIO.getTelefone());
        assertThat(testeDto.getEmail()).isEqualTo(FUNCIONARIO.getEmail());
    }

}
