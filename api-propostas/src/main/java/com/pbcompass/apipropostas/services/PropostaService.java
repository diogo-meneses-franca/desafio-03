package com.pbcompass.apipropostas.services;

import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.dto.mapper.MapperGenerico;
import com.pbcompass.apipropostas.entities.Proposta;
import com.pbcompass.apipropostas.exception.custom.RecursoNaoEncontrado;
import com.pbcompass.apipropostas.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository repository;

    @Transactional(readOnly = true)
    public Proposta buscarPorId(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new RecursoNaoEncontrado(String.format("Proposta com o id %d não encontrada", id)));
    }

    @Transactional(readOnly = true)
    public Page<PropostaRespostaDto> buscarTodos(Pageable pageable) {
        Page<Proposta> propostas = repository.findAll(pageable);
        Page<PropostaRespostaDto> propostasDto = propostas.map(f -> MapperGenerico.toDto(f, PropostaRespostaDto.class));
        return propostasDto;
    }

}
