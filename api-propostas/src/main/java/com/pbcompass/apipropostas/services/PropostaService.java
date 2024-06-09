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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository repository;
    private final FuncionarioFeignClient feignClient;

    @Transactional(readOnly = true)
    public PropostaRespostaDto buscarPorId(Long id){
        Proposta proposta =  repository.findById(id).orElseThrow(
                ()-> new RecursoNaoEncontrado(String.format("Proposta com o id %d não encontrada", id)));
        PropostaRespostaDto resposta = MapperGenerico.toDto(proposta, PropostaRespostaDto.class);
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
        FuncionarioRespostaDto funcionarioRespostaDto;
        Proposta proposta = MapperGenerico.toEntity(dto, Proposta.class);
        try {
            funcionarioRespostaDto = feignClient.buscarPorId(funcionarioId).getBody();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            throw  new ErroAoBuscarFuncionarioException(String.format("Erro ao buscar funcionario com o id %d", funcionarioId));
        }
        proposta.setFuncionarioId(funcionarioId);
        Proposta propostaSalva = repository.save(proposta);
        PropostaRespostaDto resposta = MapperGenerico.toDto(propostaSalva, PropostaRespostaDto.class);
        resposta.setCriador(funcionarioRespostaDto);
        return resposta;
    }

}
