package com.pbcompass.apifuncionarios.controller.v1;

import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.dto.mapper.FuncionarioMapper;
import com.pbcompass.apifuncionarios.entities.Funcionario;
import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.exception.MensagemErroPadrao;
import com.pbcompass.apifuncionarios.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Funcionários", description = "Contém todas as operações relativas ao cadastro, localização, edição e exclusão de alunos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    @Operation(summary = "Cadastra um novo funcionário",
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

    @Operation(summary = "Buscar um funcionário pelo seu id",
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioRespostaDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Funcionário com o id não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))
                    ),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioRespostaDto> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = service.buscarPorId(id);
        FuncionarioRespostaDto resposta = FuncionarioMapper.toDto(funcionario, FuncionarioRespostaDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @Operation(summary = "Deleta um funcionário pelo seu id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Sucesso sem conteúdo",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Funcionário com o id não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Altera os dados de um funcionário",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso alterado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioRespostaDto.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Corpo requisição invalido",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Tentativa de modificar o CPF cadastrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item a atualizar não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioRespostaDto> editar(@PathVariable Long id,
                                                         @Valid @RequestBody FuncionarioCadastrarDto dto){
        Funcionario funcionario = FuncionarioMapper.toEntity(dto, Funcionario.class);
        FuncionarioRespostaDto resposta = FuncionarioMapper.toDto(service.editar(id, funcionario), FuncionarioRespostaDto.class);
        return ResponseEntity.ok(resposta);
    }


    @Operation(summary = "Busca todos os funcionários paginados",
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FuncionarioRespostaDto.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<Page<FuncionarioRespostaDto>> buscarTodos(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction){
        var sordDirection = "desc".equalsIgnoreCase(direction) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sordDirection, "nome"));
        return ResponseEntity.ok(service.buscarTodos(pageable));
    }
}
