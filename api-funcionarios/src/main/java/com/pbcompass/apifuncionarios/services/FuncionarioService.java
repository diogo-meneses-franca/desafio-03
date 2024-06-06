package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.DadosUnicosException;
import com.pbcompass.apifuncionarios.exception.custom.ErroAoSalvarFuncionario;
import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
            var entidade = repository.findById(id);
            return repository.saveAndFlush(funcionario);
        } catch (DataIntegrityViolationException e) {
            throw new ErroAoSalvarFuncionario("Erro ao atualizar funcionário no banco de dados");
        }
    }
}
