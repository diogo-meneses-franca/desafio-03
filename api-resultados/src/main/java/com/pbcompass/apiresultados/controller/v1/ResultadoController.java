package com.pbcompass.apiresultados.controller.v1;

import com.pbcompass.apiresultados.dto.ResultadoCadastrarDto;
import com.pbcompass.apiresultados.dto.ResultadoRespostaDto;
import com.pbcompass.apiresultados.exception.custom.MensagemErroPadrao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/resultados")
public interface ResultadoController {

    @Operation(
            summary = "Cadastrar um resultado de uma proposta",
            description = "Endpoint para cadastrar um resultado de uma proposta. " +
                    "Necessário o id da proposta e a decisão dos votos, sendo esse campo " +
                    "composto por APROVAR ou REJEITAR apenas.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação de uma nova proposta",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ResultadoCadastrarDto.class))
            ),
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResultadoCadastrarDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro ao buscar funcionario com o id fornecido",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @PostMapping
    ResponseEntity<ResultadoRespostaDto> cadastrar(@RequestBody @Valid ResultadoCadastrarDto dto);

    @Operation(summary = "Buscar o resultado de uma proposta por id",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Id do resultado",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long"))
            },
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResultadoRespostaDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resultado não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ResultadoRespostaDto> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Busca todas os resultados de propostas paginadas",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "numero da pagina",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "Integer")),
                    @Parameter(
                            name = "size",
                            description = "elementos por pagina",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "Integer")),
                    @Parameter(
                            name = "direction",
                            description = "direção da ordenação, asc para ascendente ou desc para descendente",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "String"))
            },
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResultadoRespostaDto.class)))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @GetMapping
    ResponseEntity<Page<ResultadoRespostaDto>> buscarTodos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

}
