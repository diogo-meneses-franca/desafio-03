package com.pbcompass.apiresultados.controller.v1;

import com.pbcompass.apiresultados.dto.ResultadoRespostaDto;
import com.pbcompass.apiresultados.exception.custom.MensagemErroPadrao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/resultados")
public interface ResultadoController {

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
