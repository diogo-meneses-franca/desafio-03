package com.pbcompass.apipropostas.services;

import com.pbcompass.apipropostas.entities.Proposta;
import com.pbcompass.apipropostas.repository.PropostaRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PropostaServiceTest {

    @Mock
    private PropostaRepository repository;

    @InjectMocks
    private PropostaService service;

    private Proposta proposta;

}
