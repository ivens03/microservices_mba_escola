package br.com.mba.spring.colegio.usuarios.controller;

import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.service.impl.ResponsavelAlunoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsaveis")
@RequiredArgsConstructor
@Tag(name = "Responsáveis", description = "Gerenciamento de responsáveis pelos alunos")
public class ResponsavelAlunoController {

    private final ResponsavelAlunoServiceImpl service;

    @Operation(summary = "Cadastrar Responsável", description = "Cria um novo responsável vinculado a um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ResponsavelAluno.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação (Campos inválidos ou dados faltantes)"),
            @ApiResponse(responseCode = "409", description = "Conflito: CPF já cadastrado")
    })
    @PostMapping
    public ResponseEntity<ResponsavelAluno> create(@RequestBody @Valid ResponsavelAlunoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createResponsavel(dto));
    }

    @Operation(summary = "Atualizar Parcialmente (PATCH)", description = "Atualiza dados do responsável. Campos nulos são ignorados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ResponsavelAluno> update(@PathVariable Long id, @RequestBody ResponsavelAlunoDTO dto) {
        return ResponseEntity.ok(service.updateResponsavel(id, dto));
    }

    @Operation(summary = "Listar todos", description = "Retorna a lista completa de responsáveis cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    })
    @GetMapping
    public ResponseEntity<List<ResponsavelAluno>> findAll() {
        return ResponseEntity.ok(service.findAllResponsaveis());
    }

    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de um responsável específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsável encontrado"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelAluno> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findResponsavelById(id));
    }

    @Operation(summary = "Inativar Responsável (Exclusão Lógica)",
            description = "Realiza a exclusão lógica do responsável, inativando o usuário vinculado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Responsável inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteResponsavel(id);
        return ResponseEntity.noContent().build();
    }
}
