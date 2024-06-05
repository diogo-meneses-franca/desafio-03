package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private FuncionarioRepository repository;
}
