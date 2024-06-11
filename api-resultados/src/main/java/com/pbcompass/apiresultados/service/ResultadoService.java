package com.pbcompass.apiresultados.service;


import com.pbcompass.apiresultados.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository repository;



}
