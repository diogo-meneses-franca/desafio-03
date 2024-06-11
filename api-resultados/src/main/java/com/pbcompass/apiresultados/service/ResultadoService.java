package com.pbcompass.apiresultados.service;


import com.pbcompass.apiresultados.dto.PropostaRespostaDto;
import com.pbcompass.apiresultados.exception.custom.ErroAoBuscarPropostaException;
import com.pbcompass.apiresultados.feign.PropostaFeignClient;
import com.pbcompass.apiresultados.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository repository;
    private PropostaFeignClient feignClient;

    private PropostaRespostaDto buscarPropostaPorId(Long propostaId) {
        PropostaRespostaDto proposta;
        try {
            proposta = feignClient.buscarPorId(propostaId).getBody();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new ErroAoBuscarPropostaException(String.format("Erro ao buscar proposta com id %d", propostaId));
        }
        return proposta;
    }
}
