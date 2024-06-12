package com.pbcompass.apifuncionarios.controller.v1;

import com.pbcompass.apifuncionarios.dto.FuncionarioCadastrarDto;
import com.pbcompass.apifuncionarios.dto.FuncionarioRespostaDto;
import com.pbcompass.apifuncionarios.exception.custom.MensagemErroPadrao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Funcionários",
        description = "Contém todas as operações relativas ao cadastro, localização, edição e exclusão de alunos"
)
@RequestMapping("/api/v1/funcionarios")
public interface FuncionarioController {


    @Operation(
            summary = "Cadastrar um novo funcionário",
            description = "Endpoint para cadastrar um novo funcionário, sendo " +
                    "todos os atributos obrigatórios. O CPF deve conter apenas números " +
                    "com tamanho máximo de 11 caracteres, já o telefone deve conter de 10 a 14 caracteres. " +
                    "Tanto o CPF quanto o email não poderão " +
                    "ser cadastrados novamente em um novo funcionário.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação de um novo funcionário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FuncionarioCadastrarDto.class))
            ),
            responses = {
                    @ApiResponse(
                            description = "Funcionário cadastrado com sucesso",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioRespostaDto.class))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Dados já cadastrados no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(responseCode = "422",
                            description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @PostMapping
    ResponseEntity<FuncionarioRespostaDto> cadastrar(@RequestBody @Valid FuncionarioCadastrarDto dto);


    @Operation(summary = "Buscar um funcionário pelo seu id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Id do funcionário",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Integer"))
            },
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioRespostaDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Funcionário com o id não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<FuncionarioRespostaDto> buscarPorId(@PathVariable Long id);

    @Operation(summary = "Deletar um funcionário pelo seu id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Sucesso sem conteúdo",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Funcionário com o id não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Long id);


    @Operation(summary = "Alterar os dados de um funcionário",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para edição de um funcionário já cadastrado contendo seu id",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FuncionarioRespostaDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso alterado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioRespostaDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Tentativa de modificar o CPF cadastrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item a atualizar não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Corpo requisição invalido",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @PutMapping
    ResponseEntity<FuncionarioRespostaDto> editar(
            @RequestBody @Valid FuncionarioRespostaDto dto
    );


    @Operation(summary = "Buscar todos os funcionários",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "numero da pagina",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "Integer")),
                    @Parameter(
                            name = "size",
                            description = "elementos por pagina",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "Integer")),
                    @Parameter(
                            name = "direction",
                            description = "direção da ordenação, asc para ascendente ou desc para descendente",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "String"))
            },
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FuncionarioRespostaDto.class)))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @GetMapping
    ResponseEntity<Page<FuncionarioRespostaDto>> buscarTodos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );
}
