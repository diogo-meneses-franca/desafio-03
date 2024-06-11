package com.pbcompass.apiresultados.service;


import com.pbcompass.apiresultados.dto.PropostaRespostaDto;
import com.pbcompass.apiresultados.dto.ResultadoRespostaDto;
import com.pbcompass.apiresultados.entities.Resultado;
import com.pbcompass.apiresultados.exception.custom.ErroAoBuscarPropostaException;
import com.pbcompass.apiresultados.exception.custom.RecursoNaoEncontrado;
import com.pbcompass.apiresultados.feign.PropostaFeignClient;
import com.pbcompass.apiresultados.repository.ResultadoRepository;
import com.pbcompass.apiresultados.service.mapper.MapperGenerico;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultadoService {

    private final ResultadoRepository repository;
    private PropostaFeignClient feignClient;

    public ResultadoRespostaDto buscarPorId(Long id) {
        Resultado resultado = repository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontrado(String.format("Resultado com o id %d não encontrado", id))
        );
        ResultadoRespostaDto resposta = MapperGenerico.toDto(resultado, ResultadoRespostaDto.class);
        return resposta;
    }

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
