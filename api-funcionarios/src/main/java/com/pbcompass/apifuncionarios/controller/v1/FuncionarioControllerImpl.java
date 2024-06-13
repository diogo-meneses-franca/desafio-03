package com.pbcompass.apifuncionarios.controller.v1;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.services.FuncionarioService;
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
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Override
    public ResponseEntity<FuncionarioRespostaDto> cadastrar(FuncionarioCadastrarDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
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
    public ResponseEntity<FuncionarioRespostaDto> editar(FuncionarioRespostaDto dto) {
        return ResponseEntity.ok(service.editar(dto));
    }
}
