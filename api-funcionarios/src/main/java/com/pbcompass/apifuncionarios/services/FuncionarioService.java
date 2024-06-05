package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.CpfUnicoException;
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

    public Funcionario cadastrar(Funcionario funcionario) {
        try {
            return repository.save(funcionario);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUnicoException("Dados já cadastrados no sistema");
        }
    }
}
