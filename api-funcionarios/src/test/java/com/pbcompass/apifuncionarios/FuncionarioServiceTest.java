package com.pbcompass.apifuncionarios;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.custom.DadosUnicosException;
import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import com.pbcompass.apifuncionarios.services.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @InjectMocks
    private FuncionarioService service;

    private Funcionario funcionario;

    @BeforeEach
    public void setUp() {
        funcionario = new Funcionario(
                1L,
                "João Silva",
                "39456376788",
                "Rua Tiradentes",
                "44991111111",
                "joao@email.com"
        );
    }

/*    @Test
    void cadastrarFuncionario_ComDadosValidos_RetornaObjetoFuncionario(){
        given(repository.save(funcionario)).willReturn(funcionario);

        Funcionario funcionarioSalvo = service.cadastrar(funcionario);
        assertNotNull(funcionarioSalvo);
        assertTrue(funcionarioSalvo.getId() > 0);
        assertEquals(funcionario.getNome(), funcionarioSalvo.getNome());
        assertEquals(funcionario.getCpf(), funcionarioSalvo.getCpf());
        assertEquals(funcionario.getEndereco(), funcionarioSalvo.getEndereco());
        assertEquals(funcionario.getTelefone(), funcionarioSalvo.getTelefone());
        assertEquals(funcionario.getEmail(), funcionarioSalvo.getEmail());
    }

    @Test
    void cadastrarFuncionario_ComDadosCpfOuEmailJaCadastrado_ThrowsDadosUnicosException(){
        given(repository.save(funcionario)).willThrow(DataIntegrityViolationException.class);
        assertThrows(DadosUnicosException.class, () -> service.cadastrar(funcionario));
    }
*/
    @Test
    void buscarPorId_ComIdValido_RetornaObjetoFuncionario(){
        given(repository.findById(anyLong())).willReturn(Optional.of(funcionario));
        FuncionarioRespostaDto funcionarioSalvo = service.buscarPorId(funcionario.getId());

        assertNotNull(funcionarioSalvo);
        assertEquals(funcionario.getId(), funcionarioSalvo.getId());
        assertEquals(funcionario.getNome(), funcionarioSalvo.getNome());
        assertEquals(funcionario.getCpf(), funcionarioSalvo.getCpf());
        assertEquals(funcionario.getEndereco(), funcionarioSalvo.getEndereco());
        assertEquals(funcionario.getTelefone(), funcionarioSalvo.getTelefone());
        assertEquals(funcionario.getEmail(), funcionarioSalvo.getEmail());
    }

    @Test
    void buscarPorId_ComIdInexistente_LancaExcecaoEntityNotFoundException(){
        given(repository.findById(anyLong())).willThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(2L));
    }
/*
    @Test
    void editar_ComDadosValidos_RetornaObjetoFuncionarioAtualizado(){
        given(repository.findById(anyLong())).willReturn(Optional.of(funcionario));
        funcionario.setNome("João Santos");
        funcionario.setEmail("joaosantos@email.com");
        given(repository.saveAndFlush(funcionario)).willReturn(funcionario);
        Funcionario funcionarioAtualizado = service.editar(funcionario.getId(), funcionario);

        assertEquals(funcionarioAtualizado.getNome(), funcionario.getNome());
        assertEquals(funcionarioAtualizado.getEmail(), funcionario.getEmail());
    }
*/
    @Test
    void excluir_ComIdValido_Void(){
        given(repository.findById(anyLong())).willReturn(Optional.of(funcionario));
        willDoNothing().given(repository).delete(funcionario);
        service.excluir(funcionario.getId());

        verify(repository, times(1)).delete(funcionario);
    }
}
