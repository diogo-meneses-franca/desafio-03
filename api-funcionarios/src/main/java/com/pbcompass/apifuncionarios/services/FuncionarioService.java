package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.mapper.FuncionarioMapper;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.custom.DadosUnicosException;
import com.pbcompass.apifuncionarios.exception.custom.AtualizacaoNaoPermitida;
import com.pbcompass.apifuncionarios.exception.custom.ErroAoSalvarFuncionario;
import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;

    @Transactional(readOnly = true)
    public Funcionario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Funcionario com o id %d não encontrado", id)));
    }

    @Transactional
    public Funcionario cadastrar(Funcionario funcionario) {
        try {
            return repository.save(funcionario);
        } catch (DataIntegrityViolationException e) {
            throw new DadosUnicosException("CPF ou Email já cadastrado no sistema");
        }
    }

    public void excluir(long id) {
        Funcionario  entidade = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("funcionários com o id %d não encontrado", id )));
        repository.delete(entidade);
    }

    @Transactional
    public Funcionario editar(Long id, Funcionario funcionario) {
        try {
            Funcionario entidade = buscarPorId(id);
            if(!entidade.getCpf().equals(funcionario.getCpf())) {
                throw new AtualizacaoNaoPermitida("Não é possível alterar o CPF cadastrado");
            }

            entidade.setNome(funcionario.getNome());
            entidade.setEndereco(funcionario.getEndereco());
            entidade.setTelefone(funcionario.getTelefone());
            entidade.setEmail(funcionario.getEmail());
            return repository.saveAndFlush(entidade);

        } catch (DataIntegrityViolationException e) {
            throw new ErroAoSalvarFuncionario("Erro ao atualizar funcionário no banco de dados");
        }
    }

    @Transactional(readOnly = true)
    public Page<FuncionarioRespostaDto> buscarTodos(Pageable pageable) {
        Page<Funcionario> funcionarios = repository.findAll(pageable);
        Page<FuncionarioRespostaDto> funcionariosDto = funcionarios.map(f -> FuncionarioMapper.toDto(f, FuncionarioRespostaDto.class));
        return funcionariosDto;
    }
}
