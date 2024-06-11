package com.pbcompass.apipropostas.services;

import com.pbcompass.apipropostas.dto.FuncionarioRespostaDto;
import com.pbcompass.apipropostas.dto.PropostaCadastrarDto;
import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.dto.VotoCadastrarDto;
import com.pbcompass.apipropostas.entities.Voto;
import com.pbcompass.apipropostas.exception.custom.*;
import com.pbcompass.apipropostas.services.mapper.MapperGenerico;
import com.pbcompass.apipropostas.entities.Proposta;
import com.pbcompass.apipropostas.feign.FuncionarioFeignClient;
import com.pbcompass.apipropostas.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public PropostaRespostaDto editar(PropostaRespostaDto dto) {
        PropostaRespostaDto proposta = buscarPorId(dto.getId());
        if(!proposta.getCriador().equals(dto.getCriador())) {
            throw new CriadorUnicoException("O criador da proposta não pode ser alterado");
        }
        Proposta aSalvar = MapperGenerico.toEntity(dto, Proposta.class);
        aSalvar.setFuncionarioId(dto.getCriador().getId());
        Proposta propostaSalva = repository.save(aSalvar);
        PropostaRespostaDto resposta = MapperGenerico.toDto(propostaSalva, PropostaRespostaDto.class);
        FuncionarioRespostaDto criador = buscarFuncionarioPorId(propostaSalva.getFuncionarioId());
        resposta.setCriador(criador);
        return resposta;
    }

    @Transactional
    public void excluir(Long id) {
        Proposta entidade = repository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontrado(String.format("Proposta com o id %d não encontrada", id)));
        repository.delete(entidade);
    }

    @Transactional
    public void votar(VotoCadastrarDto dto) {
        buscarFuncionarioPorId(dto.getFuncionarioId());
        Proposta proposta = repository.findById(dto.getPropostaId()).orElseThrow(
                () -> new RecursoNaoEncontrado(String.format("Proposta com o id %d não encontrada", dto.getPropostaId())));
        Long inicioDaVotacao = proposta.getInicioVotacao().getTime();
        Long duracaoEmMilisegundos = proposta.getDuracaoEmMinutos() * 60 * 1000L;
        long fimDaVotacao = inicioDaVotacao + duracaoEmMilisegundos;
        long momentoDoVoto = new Date().getTime();
        if(momentoDoVoto > fimDaVotacao) {
            throw new VotoInvalidoException("A votação desta proposta já foi encerrada!");
        }
        List<Voto> votos = proposta.getVotos();
        log.error(Arrays.toString(votos.toArray()));
        Voto voto = dto.toVoto();
        votos.forEach(obj -> {
            if(obj.getFuncionarioId().equals(voto.getFuncionarioId())) {
                throw new VotoInvalidoException("É permitido somente um voto por id!");
            }
        });
        voto.setProposta(proposta);
        votos.add(voto);
        repository.save(proposta);
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
