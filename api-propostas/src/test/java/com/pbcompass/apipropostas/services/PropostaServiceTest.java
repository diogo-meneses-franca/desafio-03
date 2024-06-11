package com.pbcompass.apipropostas.services;

import com.pbcompass.apipropostas.dto.FuncionarioRespostaDto;
import com.pbcompass.apipropostas.dto.PropostaCadastrarDto;
import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.entities.Proposta;
import com.pbcompass.apipropostas.feign.FuncionarioFeignClient;
import com.pbcompass.apipropostas.repository.PropostaRepository;
import com.pbcompass.apipropostas.services.mapper.MapperGenerico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PropostaServiceTest {

    @Mock
    private PropostaRepository repository;

    @Mock
    private FuncionarioFeignClient feignClient;

    @InjectMocks
    private PropostaService service;

    private Proposta proposta;

    public static final FuncionarioRespostaDto FUNCIONARIO = new FuncionarioRespostaDto(
            1L,
            "Teste",
            "85789599060",
            "Endereço Teste",
            "43998776655",
            "teste@email.com"
    );

    @BeforeEach
    void setUp() {
        proposta = new Proposta(
                1L,
                "PropostaNome",
                "descrição da proposta",
                1L,
                1,
                new Date(),
                new ArrayList<>()
        );
    }

    @Test
    void excluirProposta_RetornarNoContent() {
        given(repository.findById(any())).willReturn(Optional.of(proposta));
        willDoNothing().given(repository).delete(proposta);
        service.excluir(proposta.getId());

        verify(repository, times(1)).delete(proposta);
    }

}
