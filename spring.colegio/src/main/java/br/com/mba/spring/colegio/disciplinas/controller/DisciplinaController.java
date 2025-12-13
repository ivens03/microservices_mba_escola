package br.com.mba.spring.colegio.disciplinas.controller;

import br.com.mba.spring.colegio.disciplinas.dto.DisciplinaDTO;
import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.service.DisciplinaService;
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
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
@Tag(name = "Disciplinas", description = "Gerenciamento de Disciplinas e Matérias")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @Operation(summary = "Criar Disciplina", description = "Cadastra uma nova disciplina no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Disciplina> create(@RequestBody @Valid DisciplinaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaService.createDisciplina(dto));
    }

    @Operation(summary = "Listar todas", description = "Retorna a lista completa de disciplinas.")
    @GetMapping
    public ResponseEntity<List<Disciplina>> findAll() {
        return ResponseEntity.ok(disciplinaService.findAllDisciplinas());
    }

    @Operation(summary = "Buscar por ID", description = "Obtém os detalhes de uma disciplina específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disciplina encontrada"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> findById(@PathVariable Long id) {
        return ResponseEntity.ok(disciplinaService.findDisciplinaById(id));
    }

    @Operation(summary = "Atualizar Disciplina", description = "Atualiza os dados de uma disciplina existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> update(@PathVariable Long id, @RequestBody @Valid DisciplinaDTO dto) {
        // Assume-se que o seu Service tenha o método updateDisciplina(Long, DisciplinaDTO)
        return ResponseEntity.ok(disciplinaService.updateDisciplina(id, dto));
    }

    @Operation(summary = "Remover Disciplina", description = "Remove uma disciplina do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disciplina removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Assume-se que o seu Service tenha o método deleteDisciplina(Long)
        disciplinaService.deleteDisciplina(id);
        return ResponseEntity.noContent().build();
    }
}
