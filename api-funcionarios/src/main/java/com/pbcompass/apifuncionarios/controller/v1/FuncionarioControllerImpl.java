package com.pbcompass.apifuncionarios.controller.v1;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.services.FuncionarioService;
import com.pbcompass.apifuncionarios.services.mapper.MapperGenerico;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FuncionarioControllerImpl implements FuncionarioController {

    private final FuncionarioService service;

    @Override
    public ResponseEntity<FuncionarioRespostaDto> buscarPorId(Long id) {
        FuncionarioRespostaDto resposta = service.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @Override
    public ResponseEntity<FuncionarioRespostaDto> cadastrar(FuncionarioCadastrarDto dto) {
        FuncionarioRespostaDto resposta = service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Override
    public ResponseEntity<Void> excluir(Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<FuncionarioRespostaDto>> buscarTodos(
            Integer page,
            Integer size,
            String direction) {
        return ResponseEntity.ok(service.buscarTodos(page, size, direction));
    }

    @Override
    public ResponseEntity<FuncionarioRespostaDto> editar(Long id, FuncionarioCadastrarDto dto) {
        Funcionario funcionario = MapperGenerico.toEntity(dto, Funcionario.class);
        FuncionarioRespostaDto resposta = MapperGenerico.toDto(service.editar(id, funcionario), FuncionarioRespostaDto.class);
        return ResponseEntity.ok(resposta);
    }
}
