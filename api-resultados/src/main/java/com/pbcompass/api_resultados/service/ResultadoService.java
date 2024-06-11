package com.pbcompass.api_resultados.service;


import com.pbcompass.api_resultados.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository repository;



}
