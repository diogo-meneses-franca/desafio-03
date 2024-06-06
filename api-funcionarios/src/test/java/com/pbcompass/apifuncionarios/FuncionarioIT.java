package com.pbcompass.apifuncionarios;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.exception.MensagemErroPadrao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/funcionarios/funcionarios-deletar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/funcionarios/funcionarios-cadastrar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class FuncionarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void buscarPorId_ComIdValido_RetornaFuncionarioRespostaDtoStatus200(){
        FuncionarioRespostaDto resposta = testClient
                .get()
                .uri("/api/v1/funcionarios/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(FuncionarioRespostaDto.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getId()).isNotNull();
        assertThat(resposta.getNome()).isEqualTo("João");
        assertThat(resposta.getCpf()).isEqualTo("10498168387");
        assertThat(resposta.getEndereco()).isEqualTo("Rua funcionario 1");
        assertThat(resposta.getTelefone()).isEqualTo("44991111111");
        assertThat(resposta.getEmail()).isEqualTo("joao@email.com");

    }

    @Test
    public void buscarPorId_ComIdInexistente_RetornaMensagemErroPadraoStatus404(){
        MensagemErroPadrao resposta = testClient
                .get()
                .uri("/api/v1/funcionarios/3")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(MensagemErroPadrao.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatus()).isEqualTo(404);
        assertThat(resposta.getError()).isEqualTo("Not found");
        assertThat(resposta.getMessage()).isEqualTo("Funcionario com o id 3 não encontrado");
        assertThat(resposta.getPath()).isEqualTo("/api/v1/funcionarios/3");

    }
}
