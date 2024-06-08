package com.pbcompass.apipropostas.services;

import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.dto.mapper.PropostaMapper;
import com.pbcompass.apipropostas.entities.Proposta;
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
    public Page<PropostaRespostaDto> buscarTodos(Pageable pageable) {
        Page<Proposta> proposta = repository.findAll(pageable);
        Page<PropostaRespostaDto> propostasDto = proposta.map(p -> PropostaMapper.toDto(p, PropostaRespostaDto.class));
        return propostasDto;
    }
}
