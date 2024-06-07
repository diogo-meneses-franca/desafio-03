package com.pbcompass.apifuncionarios;

import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.exception.MensagemErroPadrao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    @Sql(scripts = "/sql/funcionarios/funcionarios-deletar.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void cadastrarFuncionario_ComDadosValidos_RetornarFuncionarioRespostaDtoStatus201(){
        FuncionarioRespostaDto resposta = testClient
                .post()
                .uri("/api/v1/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("Funcionario", "47276131076", "Rua Teste", "41999995888", "teste@email.com"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FuncionarioRespostaDto.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getId()).isNotNull();
        assertThat(resposta.getNome()).isEqualTo("Funcionario");
        assertThat(resposta.getCpf()).isEqualTo("47276131076");
        assertThat(resposta.getEndereco()).isEqualTo("Rua Teste");
        assertThat(resposta.getTelefone()).isEqualTo("41999995888");
        assertThat(resposta.getEmail()).isEqualTo("teste@email.com");
    }

    @Test
    public void cadastrarFuncionario_ComCpfJaCadastrado_RetornarMensagemErroPadraoStatus409(){
        MensagemErroPadrao resposta = testClient
                .post()
                .uri("/api/v1/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("Funcionario", "10498168387", "Rua Teste", "41999995888", "teste@email.com"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(MensagemErroPadrao.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatus()).isEqualTo(409);
        assertThat(resposta.getError()).isEqualTo("Erro ao cadastrar funcionário");
        assertThat(resposta.getMessage()).isEqualTo("CPF ou Email já cadastrado no sistema");
        assertThat(resposta.getPath()).isEqualTo("/api/v1/funcionarios");
    }

    @Test
    public void cadastrarFuncionario_ComEmailJaCadastrado_RetornarMensagemErroPadraoStatus409(){
        MensagemErroPadrao resposta = testClient
                .post()
                .uri("/api/v1/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("Funcionario", "47276131076", "Rua Teste", "41999995888", "joao@email.com"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(MensagemErroPadrao.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatus()).isEqualTo(409);
        assertThat(resposta.getError()).isEqualTo("Erro ao cadastrar funcionário");
        assertThat(resposta.getMessage()).isEqualTo("CPF ou Email já cadastrado no sistema");
        assertThat(resposta.getPath()).isEqualTo("/api/v1/funcionarios");
    }

    @Test
    public void cadastrarFuncionario_ComDadosInvalidos_RetornarMensagemErroPadraoStatus422(){
        MensagemErroPadrao resposta = testClient
                .post()
                .uri("/api/v1/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("Funcionario", "000000000000000", "Rua Teste", "000000000000000", "joao@email.com"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(MensagemErroPadrao.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatus()).isEqualTo(422);
        assertThat(resposta.getError()).isEqualTo("Erro ao cadastrar funcionário");
        assertThat(resposta.getMessage()).isEqualTo("Dados de entrada inválidos");
        assertThat(resposta.getPath()).isEqualTo("/api/v1/funcionarios");
    }

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

    @Test
    public void editarFuncionario_ComDadosValidos_RetornarFuncionarioRespostaDtoStatus200() {
        FuncionarioRespostaDto resposta = testClient
                .put()
                .uri("/api/v1/funcionarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("Funcionario Teste Editado", "10498168387", "Rua Teste", "43999887766", "teste@email.com"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(FuncionarioRespostaDto.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getId()).isNotNull();
        assertThat(resposta.getNome()).isEqualTo("Funcionario Teste Editado");
        assertThat(resposta.getCpf()).isEqualTo("10498168387");
        assertThat(resposta.getEndereco()).isEqualTo("Rua Teste");
        assertThat(resposta.getTelefone()).isEqualTo("43999887766");
        assertThat(resposta.getEmail()).isEqualTo("teste@email.com");
    }

    @Test
    public void editarFuncionario_ComIdInexistente_RetornarStatus404() {
        MensagemErroPadrao resposta = testClient
                .put()
                .uri("/api/v1/funcionarios/10")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("Funcionario Teste Editado", "84319254007", "Rua Teste", "43999887766", "teste@email.com"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(MensagemErroPadrao.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatus()).isEqualTo(404);
        assertThat(resposta.getMessage()).isEqualTo("Funcionario com o id 10 não encontrado");
    }

    @Test
    public void editarFuncionario_ComDadosInvalidos_RetornarStatus422() {
        MensagemErroPadrao resposta = testClient
                .put()
                .uri("/api/v1/funcionarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("", "", "", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(MensagemErroPadrao.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatus()).isEqualTo(422);
    }

    @Test
    public void editarFuncionario_TentativaModificarCPF_RetornarStatus403() {
        MensagemErroPadrao resposta = testClient
                .put()
                .uri("/api/v1/funcionarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FuncionarioCadastrarDto("Funcionario Teste Editado", "83011349096", "Rua Teste", "43999887766", "teste@email.com"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(MensagemErroPadrao.class)
                .returnResult().getResponseBody();

        assertThat(resposta).isNotNull();
        assertThat(resposta.getStatus()).isEqualTo(403);
    }

    @Test
    public void deletar_ComIdExistente_RetornandoStatus204() {
        testClient
                .delete()
                .uri("/api/v1/funcionarios/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void deletar_ComIdInexistente_RetornandoStatus404() {
        testClient
                .delete()
                .uri("/api/v1/funcionarios/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void deletar_ComIdInvalido_RetornandoStatus400() {
        testClient
                .delete()
                .uri("/api/v1/funcionarios/abc")
                .exchange()
                .expectStatus().isBadRequest();
    }
}
