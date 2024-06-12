package com.pbcompass.apiresultados.service;

import com.pbcompass.apiresultados.dto.PropostaRespostaDto;

import com.pbcompass.apiresultados.dto.ResultadoCadastrarDto;
import com.pbcompass.apiresultados.dto.ResultadoRespostaDto;
import com.pbcompass.apiresultados.entities.Resultado;
import com.pbcompass.apiresultados.exception.custom.ErroAoBuscarPropostaException;
import com.pbcompass.apiresultados.exception.custom.RecursoNaoEncontrado;
import com.pbcompass.apiresultados.feign.PropostaFeignClient;
import com.pbcompass.apiresultados.repository.ResultadoRepository;
import com.pbcompass.apiresultados.service.mapper.MapperGenerico;
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
public class ResultadoService {

    private final ResultadoRepository repository;
    private final PropostaFeignClient feignClient;

    @Transactional
    public ResultadoRespostaDto cadastrar(ResultadoCadastrarDto dto) {
        PropostaRespostaDto proposta = feignClient.buscarPorId(dto.getPropostaid()).getBody();
        Resultado resultado = MapperGenerico.toEntity(dto, Resultado.class);
        ResultadoRespostaDto respostaSalva = MapperGenerico.toDto(repository.save(resultado), ResultadoRespostaDto.class);
        respostaSalva.setProposta(proposta);
        return respostaSalva;
    }

    @Transactional(readOnly = true)
    public ResultadoRespostaDto buscarPorId(Long id) {
        Resultado resultado = repository.findById(id).orElseThrow(
                () -> new RecursoNaoEncontrado(String.format("Resultado com o id %d não encontrado", id))
        );
        ResultadoRespostaDto resposta = MapperGenerico.toDto(resultado, ResultadoRespostaDto.class);
        return resposta;
    }

    @Transactional(readOnly = true)
    public Page<ResultadoRespostaDto> buscarTodos(Integer page, Integer size, String direction) {
        var sordDirection = "desc".equalsIgnoreCase(direction) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sordDirection, "id"));
        Page<Resultado> resultados = repository.findAll(pageable);
        Page<ResultadoRespostaDto> resultadosDto = resultados.map(f -> MapperGenerico.toDto(f, ResultadoRespostaDto.class));
        return resultadosDto;
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
