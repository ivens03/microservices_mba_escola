package br.com.mba.spring.colegio.usuarios.service.impl;

import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface ResponsavelAlunoServiceImpl {

    @Operation(summary = "Cadastrar Responsável", description = "Cria um novo responsável vinculado a um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Responsável cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponsavelAluno createResponsavel(ResponsavelAlunoDTO dto);

    @Operation(summary = "Atualizar Responsável", description = "Atualiza dados do responsável e do usuário vinculado.")
    ResponsavelAluno updateResponsavel(Long id, ResponsavelAlunoDTO dto);

    @Operation(summary = "Listar todos", description = "Retorna todos os responsáveis.")
    List<ResponsavelAluno> findAllResponsaveis();

    @Operation(summary = "Buscar por ID")
    ResponsavelAluno findResponsavelById(Long id);

    @Operation(summary = "Remover Responsável")
    void deleteResponsavel(Long id);
}
