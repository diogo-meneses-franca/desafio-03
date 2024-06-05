package com.pbcompass.apifuncionarios.controller.v1;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.mapper.FuncionarioMapper;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.services.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioRespostaDto> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = service.buscarPorId(id);
        FuncionarioRespostaDto resposta = FuncionarioMapper.toDto(funcionario, FuncionarioRespostaDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }
}
