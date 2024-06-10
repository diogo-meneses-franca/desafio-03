package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.services.mapper.MapperGenerico;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.custom.DadosUnicosException;
import com.pbcompass.apifuncionarios.exception.custom.AtualizacaoNaoPermitida;
import com.pbcompass.apifuncionarios.exception.custom.ErroAoSalvarFuncionario;
import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;

    @Transactional(readOnly = true)
    public FuncionarioRespostaDto buscarPorId(Long id) {
        Funcionario funcionario = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Funcionario com o id %d não encontrado", id)));

        FuncionarioRespostaDto resposta = MapperGenerico.toDto(funcionario, FuncionarioRespostaDto.class);
        return resposta;
    }

    @Transactional
    public FuncionarioRespostaDto cadastrar(FuncionarioCadastrarDto dto) {
        try {
            Funcionario funcionario = MapperGenerico.toEntity(dto, Funcionario.class);
            Funcionario funcionarioSalvo = repository.save(funcionario);
            FuncionarioRespostaDto resposta = MapperGenerico.toDto(funcionarioSalvo, FuncionarioRespostaDto.class);
            return resposta;

        } catch (DataIntegrityViolationException e) {
            throw new DadosUnicosException("CPF ou Email já cadastrado no sistema");
        }
    }

    public void excluir(long id) {
        Funcionario entidade = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("funcionários com o id %d não encontrado", id)));
        repository.delete(entidade);
    }

    @Transactional
    public FuncionarioRespostaDto editar(FuncionarioRespostaDto dto) {
        try {
            FuncionarioRespostaDto entidade = buscarPorId(dto.getId());
            if (!entidade.getCpf().equals(dto.getCpf())) {
                throw new AtualizacaoNaoPermitida("Não é possível alterar o CPF cadastrado");
            }
            Funcionario funcionario = MapperGenerico.toEntity(dto, Funcionario.class);
            Funcionario editado = repository.saveAndFlush(funcionario);
            FuncionarioRespostaDto resposta = MapperGenerico.toDto(editado, FuncionarioRespostaDto.class);
            return resposta;

        } catch (DataIntegrityViolationException e) {
            throw new ErroAoSalvarFuncionario("Erro ao atualizar funcionário no banco de dados");
        }
    }

    @Transactional(readOnly = true)
    public Page<FuncionarioRespostaDto> buscarTodos(Integer page, Integer size, String direction) {
        var sordDirection = "desc".equalsIgnoreCase(direction) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sordDirection, "nome"));
        Page<Funcionario> funcionarios = repository.findAll(pageable);
        Page<FuncionarioRespostaDto> funcionariosDto = funcionarios.map(f -> MapperGenerico.toDto(f, FuncionarioRespostaDto.class));
        return funcionariosDto;
    }
}
