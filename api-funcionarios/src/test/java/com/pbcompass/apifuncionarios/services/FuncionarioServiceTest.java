package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.custom.DadosUnicosException;
import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import com.pbcompass.apifuncionarios.services.mapper.MapperGenerico;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static com.pbcompass.apifuncionarios.constantes.FuncionarioConstantes.FUNCIONARIO;
import static com.pbcompass.apifuncionarios.constantes.FuncionarioConstantes.FUNC_CADASTRAR_DTO;
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

    @Test
    void cadastrarFuncionario_ComDadosValidos_RetornaObjetoFuncionario(){
        given(repository.save(any(Funcionario.class))).willReturn(FUNCIONARIO);

        FuncionarioRespostaDto respostaDto = service.cadastrar(FUNC_CADASTRAR_DTO);
        Funcionario funcionarioSalvo = MapperGenerico.toEntity(respostaDto, Funcionario.class);
        assertNotNull(funcionarioSalvo);
        assertTrue(FUNCIONARIO.getId().equals(funcionarioSalvo.getId()));
        assertEquals(FUNCIONARIO.getNome(), funcionarioSalvo.getNome());
        assertEquals(FUNCIONARIO.getCpf(), funcionarioSalvo.getCpf());
        assertEquals(FUNCIONARIO.getEndereco(), funcionarioSalvo.getEndereco());
        assertEquals(FUNCIONARIO.getTelefone(), funcionarioSalvo.getTelefone());
        assertEquals(FUNCIONARIO.getEmail(), funcionarioSalvo.getEmail());
    }

    @Test
    void cadastrarFuncionario_ComDadosCpfOuEmailJaCadastrado_ThrowsDadosUnicosException(){
        doThrow(DadosUnicosException.class).when(repository).save(any(Funcionario.class));

        assertThrows(DadosUnicosException.class, () -> service.cadastrar(FUNC_CADASTRAR_DTO));
    }

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

    @Test
    void excluir_ComIdValido_Void(){
        given(repository.findById(anyLong())).willReturn(Optional.of(funcionario));
        willDoNothing().given(repository).delete(funcionario);
        service.excluir(funcionario.getId());

        verify(repository, times(1)).delete(funcionario);
    }

    @Test
    void excluir_ComIdInexistente_Void(){
        given(repository.findById(anyLong())).willThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> service.excluir(funcionario.getId()));
    }
}
