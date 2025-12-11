package br.com.mba.spring.colegio.usuarios.service.impl;

import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.enums.Turno;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface AlunoServiceImpl {

    @Operation(summary = "Criar Aluno", description = "Cria um registro de aluno vinculado a um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno matriculado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    Aluno createAluno(AlunoDTO dto);

    @Operation(summary = "Atualizar Aluno", description = "Atualiza dados acadêmicos e pessoais.")
    Aluno updateAluno(Long id, AlunoDTO dto);

    @Operation(summary = "Listar Alunos", description = "Retorna todos os alunos.")
    List<Aluno> findAllAlunos();

    @Operation(summary = "Buscar Aluno por ID")
    Aluno findAlunoById(Long id);

    @Operation(summary = "Buscar por Sala", description = "Filtra alunos por sala de aula.")
    List<Aluno> findAlunosBySala(String sala);

    @Operation(summary = "Buscar por Turno", description = "Filtra alunos por turno.")
    List<Aluno> findAlunosByTurno(Turno turno);

    @Operation(summary = "Remover Aluno")
    void deleteAluno(Long id);

}
