package com.pbcompass.apifuncionarios.controller.v1;

import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.mapper.FuncionarioMapper;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.exception.MensagemErroPadrao;
import com.pbcompass.apifuncionarios.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Funcionários", description = "Contém todas as operações relativas ao cadastro, localização, edição e exclusão de alunos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    @Operation(summary = "Cadastrar um novo funcionário",
            responses = {
                    @ApiResponse(
                            description = "Funcionário cadastrado com sucesso",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioRespostaDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Dados já cadastrados no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))
                    ),
                    @ApiResponse(responseCode = "422",
                            description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<FuncionarioRespostaDto> cadastrar(@RequestBody @Valid FuncionarioCadastrarDto dto) {
        Funcionario funcionario = FuncionarioMapper.toEntity(dto, Funcionario.class);
        FuncionarioRespostaDto resposta = FuncionarioMapper.toDto(service.cadastrar(funcionario), FuncionarioRespostaDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

}
