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

    @Test
    public void toEntity_RetornarFuncionario() {

        Funcionario testeDto = FuncionarioMapper.toEntity(FUNC_CADASTRAR_DTO, Funcionario.class);

        assertThat(testeDto.getNome()).isEqualTo(FUNC_CADASTRAR_DTO.getNome());
        assertThat(testeDto.getCpf()).isEqualTo(FUNC_CADASTRAR_DTO.getCpf());
        assertThat(testeDto.getEndereco()).isEqualTo(FUNC_CADASTRAR_DTO.getEndereco());
        assertThat(testeDto.getTelefone()).isEqualTo(FUNC_CADASTRAR_DTO.getTelefone());
        assertThat(testeDto.getEmail()).isEqualTo(FUNC_CADASTRAR_DTO.getEmail());
    }

    @Test
    public void toListDto_RetornarListaFuncionarioRespostaDto() {

        FUNCIONARIO_LIST.add(FUNCIONARIO);

        List<FuncionarioRespostaDto> testeList = FuncionarioMapper.toDtoList(FUNCIONARIO_LIST, FuncionarioRespostaDto.class);

        FuncionarioRespostaDto dto = testeList.get(0);

        assertThat(dto.getId()).isEqualTo(FUNCIONARIO.getId());
        assertThat(dto.getNome()).isEqualTo(FUNCIONARIO.getNome());
        assertThat(dto.getCpf()).isEqualTo(FUNCIONARIO.getCpf());
        assertThat(dto.getEndereco()).isEqualTo(FUNCIONARIO.getEndereco());
        assertThat(dto.getTelefone()).isEqualTo(FUNCIONARIO.getTelefone());
        assertThat(dto.getEmail()).isEqualTo(FUNCIONARIO.getEmail());
    }
    
}
