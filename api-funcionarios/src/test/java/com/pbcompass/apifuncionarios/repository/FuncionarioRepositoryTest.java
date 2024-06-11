package com.pbcompass.apifuncionarios.repository;

import com.pbcompass.apifuncionarios.entities.Funcionario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository repository;

    @DisplayName("Dado um objeto Funcionario quando salvo retorne o Funcionario com id definido")
    @Test
    void testDadoObjetoFuncionario_QuandoSalvo_RetorneFuncionarioSalvo(){
        Funcionario funcionario = new Funcionario(null, "Leandro", "25944092432", "Rua do Leandro", "44993333333", "leandro@email.com");
        Funcionario funcionarioSalvo = repository.save(funcionario);
        assertNotNull(funcionarioSalvo);
        assertTrue(funcionarioSalvo.getId() > 0);
        assertEquals(funcionario.getNome(), funcionarioSalvo.getNome());
        assertEquals(funcionario.getEmail(), funcionarioSalvo.getEmail());
        assertEquals(funcionario.getTelefone(), funcionarioSalvo.getTelefone());
        assertEquals(funcionario.getEndereco(), funcionarioSalvo.getEndereco());
        assertEquals(funcionario.getCpf(), funcionarioSalvo.getCpf());
    }

    @DisplayName("Dados dois Funcionarios salvos quando buscar todos retorne uma List<Funcionario>")
    @Test
    void testDadaListaDeFuncionarios_QuandoBuscarTodos_RetorneListFuncionarios(){
        Funcionario funcionario1 = new Funcionario(null, "Leandro", "25944092432", "Rua do Leandro", "44993333333", "leandro@email.com");
        Funcionario funcionario2 = new Funcionario(null, "Ana", "35738931475", "Rua da Ana", "44994444444", "ana@email.com");
        Funcionario funcionario1Salvo = repository.save(funcionario1);
        Funcionario funcionario2Salvo = repository.save(funcionario2);
        List<Funcionario> funcionarioList = repository.findAll();
        assertNotNull(funcionarioList);
        assertEquals(2, funcionarioList.size());
    }

    @DisplayName("Dado objeto Funcionario quando buscarPorId retorne Funcionario")
    @Test
    void testDadoObjetoFuncionario_QuandoBuscarPorId_RetorneFuncionario(){
        Funcionario funcionario = new Funcionario(null, "Leandro", "25944092432", "Rua do Leandro", "44993333333", "leandro@email.com");
        repository.save(funcionario);
        Funcionario funcionarioSalvo = repository.findById(funcionario.getId()).get();
        assertNotNull(funcionarioSalvo);
        assertEquals(funcionarioSalvo.getId(), funcionario.getId());
        assertEquals(funcionario.getNome(), funcionarioSalvo.getNome());
        assertEquals(funcionario.getEmail(), funcionarioSalvo.getEmail());
        assertEquals(funcionario.getTelefone(), funcionarioSalvo.getTelefone());
        assertEquals(funcionario.getEndereco(), funcionarioSalvo.getEndereco());
        assertEquals(funcionario.getCpf(), funcionarioSalvo.getCpf());
    }

    @DisplayName("Dado objeto Funcionario quando exclui-lo e buscar-lo retorne um Optinal vazio")
    @Test
    void testDadoObjetoFuncionario_QuandoExcluirEBuscar_RetorneOptionalVazio(){
        Funcionario funcionario = new Funcionario(null, "Leandro", "25944092432", "Rua do Leandro", "44993333333", "leandro@email.com");
        repository.save(funcionario);
        repository.delete(funcionario);
        Optional<Funcionario> funcionarioExcluido = repository.findById(funcionario.getId());
        assertTrue(funcionarioExcluido.isEmpty());
    }

    @DisplayName("Dado objeto Funcionario quando atualiza-lo retorne Funcionario atualizado")
    @Test
    void testDadoObjetoFuncionario_QuandoAtualizar_RetorneFuncionarioAtualizado(){
        Funcionario funcionario = new Funcionario(null, "Leandro", "25944092432", "Rua do Leandro", "44993333333", "leandro@email.com");
        Funcionario funcionarioSalvo = repository.save(funcionario);
        funcionarioSalvo.setNome("Leonardo");
        funcionarioSalvo.setEmail("leonardo@email.com");
        Funcionario funcionarioAtualizado = repository.save(funcionarioSalvo);
        assertNotNull(funcionarioAtualizado);
        assertEquals(funcionarioAtualizado.getNome(), "Leonardo");
        assertEquals(funcionarioAtualizado.getEmail(), "leonardo@email.com");
    }
}
