package com.pbcompass.apifuncionarios.controller.v1;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.mapper.FuncionarioMapper;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.exception.custom.MensagemErroPadrao;
import com.pbcompass.apifuncionarios.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FuncionarioControllerImpl implements FuncionarioController {

    private final FuncionarioService service;

    @Override
    public ResponseEntity<FuncionarioRespostaDto> cadastrar(FuncionarioCadastrarDto dto) {
        Funcionario funcionario = FuncionarioMapper.toEntity(dto, Funcionario.class);
        FuncionarioRespostaDto resposta = FuncionarioMapper.toDto(service.cadastrar(funcionario), FuncionarioRespostaDto.class);
        log.info("Novo funcionário criado.");
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Override
    public ResponseEntity<FuncionarioRespostaDto> buscarPorId(Long id) {
        Funcionario funcionario = service.buscarPorId(id);
        FuncionarioRespostaDto resposta = FuncionarioMapper.toDto(funcionario, FuncionarioRespostaDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @Override
    public ResponseEntity<?> excluir(Long id) {
        service.excluir(id);
        log.info("Funcionário excluído da base de dados.");
        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<FuncionarioRespostaDto> editar(Long id, FuncionarioCadastrarDto dto) {
        Funcionario funcionario = FuncionarioMapper.toEntity(dto, Funcionario.class);
        FuncionarioRespostaDto resposta = FuncionarioMapper.toDto(service.editar(id, funcionario), FuncionarioRespostaDto.class);
        return ResponseEntity.ok(resposta);
    }


    @Override
    public ResponseEntity<Page<FuncionarioRespostaDto>> buscarTodos(Integer page, Integer size, String direction) {
        var sordDirection = "desc".equalsIgnoreCase(direction) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sordDirection, "nome"));
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }
}
