package com.pbcompass.apipropostas.services;

import com.pbcompass.apipropostas.dto.FuncionarioRespostaDto;
import com.pbcompass.apipropostas.dto.PropostaCadastrarDto;
import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.services.mapper.MapperGenerico;
import com.pbcompass.apipropostas.entities.Proposta;
import com.pbcompass.apipropostas.exception.ErroAoBuscarFuncionarioException;
import com.pbcompass.apipropostas.feign.FuncionarioFeignClient;
import com.pbcompass.apipropostas.exception.custom.RecursoNaoEncontrado;
import com.pbcompass.apipropostas.repository.PropostaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository repository;
    private final FuncionarioFeignClient feignClient;

    @Transactional(readOnly = true)
    public PropostaRespostaDto buscarPorId(Long id) {
        Proposta proposta = repository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontrado(String.format("Proposta com o id %d não encontrada", id)));
        FuncionarioRespostaDto funcionario = buscarFuncionarioPorId(proposta.getFuncionarioId());
        PropostaRespostaDto resposta = MapperGenerico.toDto(proposta, PropostaRespostaDto.class);
        resposta.setCriador(funcionario);
        return resposta;
    }

    @Transactional(readOnly = true)
    public Page<PropostaRespostaDto> buscarTodos(Integer page, Integer size, String direction) {
        var sordDirection = "desc".equalsIgnoreCase(direction) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sordDirection, "nome"));
        Page<Proposta> propostas = repository.findAll(pageable);
        Page<PropostaRespostaDto> propostasDto = propostas.map(f -> MapperGenerico.toDto(f, PropostaRespostaDto.class));
        return propostasDto;
    }

    @Transactional
    public PropostaRespostaDto cadastrar(Long funcionarioId, PropostaCadastrarDto dto) {

        Proposta proposta = MapperGenerico.toEntity(dto, Proposta.class);
        if (proposta.getInicioVotacao() == null){
            proposta.setInicioVotacao(new Date());
        }
        if (proposta.getDuracaoEmMinutos() == null){
            proposta.setDuracaoEmMinutos(1);
        }
        FuncionarioRespostaDto funcionarioRespostaDto =  buscarFuncionarioPorId(funcionarioId);
        proposta.setFuncionarioId(funcionarioId);
        Proposta propostaSalva = repository.save(proposta);
        PropostaRespostaDto resposta = MapperGenerico.toDto(propostaSalva, PropostaRespostaDto.class);
        resposta.setCriador(funcionarioRespostaDto);
        return resposta;
    }

    @Transactional
    public PropostaRespostaDto editar(Long id, PropostaCadastrarDto dto) {
        PropostaRespostaDto proposta = buscarPorId(id);
        proposta.setNome(dto.getNome());
        proposta.setDescricao(dto.getDescricao());
        proposta.setDuracaoEmMinutos(dto.getDuracaoEmMinutos());
        proposta.setInicioVotacao(dto.getInicioVotacao());
        Proposta propostaSalva = repository.saveAndFlush(MapperGenerico.toEntity(proposta, Proposta.class));
        return proposta;
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Proposta excluida");

        Proposta entidade = repository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontrado(String.format("Proposta com o id %d não encontrada", id)));
        repository.delete(entidade);
    }

    private FuncionarioRespostaDto buscarFuncionarioPorId(Long funcionarioId) {
        FuncionarioRespostaDto funcionario;
        try{
            funcionario = feignClient.buscarPorId(funcionarioId).getBody();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ErroAoBuscarFuncionarioException(String.format("Erro ao buscar funcionario com o id %d", funcionarioId));
        }
        return funcionario;
    }

}
