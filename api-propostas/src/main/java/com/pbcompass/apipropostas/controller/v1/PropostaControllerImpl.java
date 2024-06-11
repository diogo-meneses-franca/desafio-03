package com.pbcompass.apipropostas.controller.v1;

import com.pbcompass.apipropostas.dto.PropostaCadastrarDto;
import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.dto.VotoCadastrarDto;
import com.pbcompass.apipropostas.entities.Voto;
import com.pbcompass.apipropostas.services.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PropostaControllerImpl implements PropostaController {

    private final PropostaService service;

    @Override
    public ResponseEntity<PropostaRespostaDto> cadastrar(Long funcionarioId, PropostaCadastrarDto dto){
        PropostaRespostaDto resposta = service.cadastrar(funcionarioId, dto);
        return ResponseEntity.ok(resposta);
    }

    @Override
    public ResponseEntity<PropostaRespostaDto> buscarPorId(Long id) {
        PropostaRespostaDto resposta = service.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @Override
    public ResponseEntity<Page<PropostaRespostaDto>> buscarTodos(
            Integer page,
            Integer size,
            String direction) {
        return ResponseEntity.ok(service.buscarTodos(page, size, direction));
    }

    @Override
    public ResponseEntity<PropostaRespostaDto> editar(PropostaRespostaDto dto) {
        PropostaRespostaDto resposta = service.editar(dto);
        return ResponseEntity.ok(resposta);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> votar(VotoCadastrarDto dto) {
        service.votar(dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Voto.Decisao> calcularResultado(Long propostaId, Long funcionarioId) {
        Voto.Decisao resposta = service.calcularResultado(propostaId, funcionarioId);
        return ResponseEntity.ok(resposta);
    }

}
