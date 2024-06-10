package com.pbcompass.apipropostas.controller.v1;

import com.pbcompass.apipropostas.dto.PropostaCadastrarDto;
import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.services.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PropostaControllerImpl implements PropostaController {

    private final PropostaService service;

    @Override
    public ResponseEntity<PropostaRespostaDto> cadastrar(Long funcionarioId, PropostaCadastrarDto dto){
        PropostaRespostaDto resposta = service.cadastrar(funcionarioId, dto);
        return ResponseEntity.ok().body(resposta);
    }

    @Override
    public ResponseEntity<PropostaRespostaDto> buscarPorId(Long id) {
        PropostaRespostaDto resposta = service.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @Override
    public ResponseEntity<Page<PropostaRespostaDto>> buscarTodos(
            Integer page,
            Integer size,
            String direction){
        return ResponseEntity.ok(service.buscarTodos(page, size, direction));
    }

    @Override
    public ResponseEntity<PropostaRespostaDto> editar(Long id, PropostaCadastrarDto dto) {
        PropostaRespostaDto resposta = service.editar(id, dto);
        return ResponseEntity.ok(resposta);
    }


}
