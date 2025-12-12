package br.com.mba.spring.colegio.usuarios.controller;

import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.service.impl.AlunoServiceImpl;
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
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Gerenciamento acadêmico de alunos")
public class AlunoController {

    private final AlunoServiceImpl alunoService;

    @Operation(summary = "Cadastrar Aluno", description = "Cria um novo registro de aluno e seu usuário base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação (CPF duplicado, dados inválidos)")
    })
    @PostMapping
    public ResponseEntity<Aluno> create(@RequestBody @Valid AlunoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.createAluno(dto));
    }

    @Operation(summary = "Atualizar Aluno (Parcial)", description = "Atualiza parcialmente dados acadêmicos e pessoais do aluno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody AlunoDTO dto) {
        return ResponseEntity.ok(alunoService.updateAluno(id, dto));
    }

    @Operation(summary = "Listar todos", description = "Retorna lista de alunos matriculados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        return ResponseEntity.ok(alunoService.findAllAlunos());
    }

    @Operation(summary = "Buscar por ID", description = "Retorna detalhes de um aluno específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.findAlunoById(id));
    }

    @Operation(summary = "Remover Aluno", description = "Inativa o aluno e o usuário vinculado do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.deleteAluno(id);
        return ResponseEntity.noContent().build();
    }
}