package com.pbcompass.apipropostas.domain;

import static com.pbcompass.apipropostas.common.PropostaConstantes.PROPOSTA;
import static com.pbcompass.apipropostas.common.PropostaConstantes.PROPOSTA_INVALIDA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.pbcompass.apipropostas.entities.Proposta;
import com.pbcompass.apipropostas.repository.PropostaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

@DataJpaTest
public class PropostaRepositoryTest {

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void afterEach() {
        PROPOSTA.setId(null);
    }

    @Test
    public void criarProposta_ComDadosValidos_RetorneCreated() {
        Proposta proposta = repository.save(PROPOSTA);

        Proposta p = testEntityManager.find(Proposta.class, proposta.getId());

        assertThat(p).isNotNull();
        assertThat(p.getNome()).isEqualTo(PROPOSTA.getNome());
        assertThat(p.getDescricao()).isEqualTo(PROPOSTA.getDescricao());
        assertThat(p.getFuncionarioId()).isEqualTo(PROPOSTA.getFuncionarioId());
        assertThat(p.getDuracaoEmMinutos()).isEqualTo(PROPOSTA.getDuracaoEmMinutos());
        assertThat(p.getInicioVotacao()).isEqualTo(PROPOSTA.getInicioVotacao());
    }

    @Test
    public void criarProposta_ComDadosInvalidos_RetorneBadRequest() {
        assertThatThrownBy(() -> repository.save(PROPOSTA_INVALIDA)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void buscarProposta_ComIdExistente_RetorneOk() {
        Proposta proposta = testEntityManager.persistFlushFind(PROPOSTA);

        Optional<Proposta> propostaOpt = repository.findById(proposta.getId());

        assertThat(propostaOpt).isNotEmpty();
        assertThat(propostaOpt.get()).isEqualTo(proposta);
    }

    @Test
    public void buscarProposta_ComIdInexistente_RetorneNotFound() {
        Optional<Proposta> propostaOpt = repository.findById(10L);

        assertThat(propostaOpt).isEmpty();
    }
}