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

    @Operation(summary = "Atualizar Aluno (Parcial)", description = "Atualiza parcialmente dados acadêmicos e pessoais do aluno. Campos omitidos não são alterados.")
    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody AlunoDTO dto) {
        // Nota: @Valid foi removido para permitir envio parcial de dados (campos nulos no JSON)
        return ResponseEntity.ok(alunoService.updateAluno(id, dto));
    }

    @Operation(summary = "Listar todos", description = "Retorna lista de alunos matriculados.")
    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        return ResponseEntity.ok(alunoService.findAllAlunos());
    }

    @Operation(summary = "Buscar por ID", description = "Retorna detalhes de um aluno específico.")
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.findAlunoById(id));
    }

    @Operation(summary = "Remover Aluno", description = "Remove o aluno e o usuário vinculado do sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.deleteAluno(id);
        return ResponseEntity.noContent().build();
    }
}