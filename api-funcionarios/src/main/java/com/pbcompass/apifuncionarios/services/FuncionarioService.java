package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.CpfUnicoException;
import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;

    public Funcionario cadastrar(Funcionario funcionario) {
        try {
            return repository.save(funcionario);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUnicoException("Dados já cadastrados no sistema");
        }
    }
}
