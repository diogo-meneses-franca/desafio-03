package com.pbcompass.apipropostas.controller.v1;

import com.pbcompass.apipropostas.dto.PropostaCadastrarDto;
import com.pbcompass.apipropostas.dto.PropostaRespostaDto;
import com.pbcompass.apipropostas.dto.ResultadoDto;
import com.pbcompass.apipropostas.dto.VotoCadastrarDto;
import com.pbcompass.apipropostas.entities.Voto;
import com.pbcompass.apipropostas.exception.custom.MensagemErroPadrao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/propostas")
public interface PropostaController {

    @Operation(
            summary = "Cadastrar uma proposta",
            description = "Endpoint para cadastrar uma nova proposta associada a um funcionário especificado pelo ID.",
            parameters = {
                    @Parameter(
                            name = "funcionarioID",
                            description = "ID do funcionário ao qual a proposta será associada",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "long"))},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação de uma nova proposta",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PropostaCadastrarDto.class))
            ),
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PropostaRespostaDto.class))),
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
    ResponseEntity<PropostaRespostaDto> cadastrar(
            @PathParam("funcionarioID") Long funcionarioId,
            @RequestBody @Valid PropostaCadastrarDto dto
    );

    @Operation(summary = "Buscar uma proposta por id",
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PropostaRespostaDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Proposta com o id não encontrada",
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
    ResponseEntity<PropostaRespostaDto> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Busca todas as propostas paginadas",
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
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PropostaRespostaDto.class)))),
                    @ApiResponse(responseCode = "500",
                            description = "Erro inesperado do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
            }
    )
    @GetMapping
    ResponseEntity<Page<PropostaRespostaDto>> buscarTodos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Edita os dados de uma proposta",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para edição de uma proposta",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PropostaRespostaDto.class))
            ),
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PropostaRespostaDto.class)))),
                    @ApiResponse(
                            description = "Proposta não encontrada",
                            responseCode = "404",
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
    @PutMapping
    ResponseEntity<PropostaRespostaDto> editar(@RequestBody @Valid PropostaRespostaDto dto);

    @Operation(summary = "Deleta um Proposta pelo seu id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Sucesso sem conteúdo",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetros inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Proposta com o id não encontrado",
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
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);


    @Operation(summary = "Vota em uma proposta",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para votar em uma proposta",
                    required = true,
                    content = @Content(schema = @Schema(implementation = VotoCadastrarDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "É permitido somente um voto por id!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "A votação desta proposta já foi encerrada!",
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
    @PutMapping("/votar")
    ResponseEntity<Void> votar(@RequestBody VotoCadastrarDto dto);

    @Operation(summary = "Divulga o resultado de uma proposta",
            parameters = {
                    @Parameter(
                            name = "propostaId",
                            description = "Id da proposta",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")),
                    @Parameter(
                            name = "funcionarioId",
                            description = "Id do funcionario",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "Integer"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sucesso",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Aguarde o encerramento da votação para divulgar o resultado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Somente o criador da proposta é autorizado a divulgar o resultado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErroPadrao.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Proposta com o id não encontrado",
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
    @PutMapping("/divulgarResultado/{propostaId}")
    ResponseEntity<ResultadoDto> divulgarResultado(@PathVariable Long propostaId, @RequestParam("funcionarioId") Long funcionarioId);

}
