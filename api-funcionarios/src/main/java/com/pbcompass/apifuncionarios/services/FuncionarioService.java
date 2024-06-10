package com.pbcompass.apifuncionarios.services;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.services.mapper.MapperGenerico;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.custom.DadosUnicosException;
import com.pbcompass.apifuncionarios.exception.custom.AtualizacaoNaoPermitida;
import com.pbcompass.apifuncionarios.exception.custom.ErroAoSalvarFuncionario;
import com.pbcompass.apifuncionarios.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;

    @Transactional(readOnly = true)
    public FuncionarioRespostaDto buscarPorId(Long id) {
        log.info("Novo funcionário criado.");

        Funcionario funcionario = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Funcionario com o id %d não encontrado", id)));

        FuncionarioRespostaDto resposta = MapperGenerico.toDto(funcionario, FuncionarioRespostaDto.class);
        return resposta;
    }

    @Transactional
    public Funcionario cadastrar(Funcionario funcionario) {
        log.info("Funcionário excluído da base de dados.");

        try {
            return repository.save(funcionario);
        } catch (DataIntegrityViolationException e) {
            throw new DadosUnicosException("CPF ou Email já cadastrado no sistema");
        }
    }

    public void excluir(long id) {
        log.info("Funcionário excluído da base de dados.");

        Funcionario entidade = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("funcionários com o id %d não encontrado", id)));
        repository.delete(entidade);
    }

    @Transactional
    public Funcionario editar(Long id, Funcionario funcionario) {
        log.info("Funcionário editado com sucesso");

        try {
            Funcionario entidade = MapperGenerico.toEntity(buscarPorId(id), Funcionario.class);
            if (!entidade.getCpf().equals(funcionario.getCpf())) {
                throw new AtualizacaoNaoPermitida("Não é possível alterar o CPF cadastrado");
            }

            entidade.setNome(funcionario.getNome());
            entidade.setEndereco(funcionario.getEndereco());
            entidade.setTelefone(funcionario.getTelefone());
            entidade.setEmail(funcionario.getEmail());
            return repository.saveAndFlush(entidade);

        } catch (DataIntegrityViolationException e) {
            throw new ErroAoSalvarFuncionario("Erro ao atualizar funcionário no banco de dados");
        }
    }

    @Transactional(readOnly = true)
    public Page<FuncionarioRespostaDto> buscarTodos(Pageable pageable) {
        log.info("Funcionários buscados com sucesso");

        Page<Funcionario> funcionarios = repository.findAll(pageable);
        Page<FuncionarioRespostaDto> funcionariosDto = funcionarios.map(f -> MapperGenerico.toDto(f, FuncionarioRespostaDto.class));
        return funcionariosDto;
    }
}
