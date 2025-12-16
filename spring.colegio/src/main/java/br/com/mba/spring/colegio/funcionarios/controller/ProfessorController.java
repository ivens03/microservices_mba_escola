package br.com.mba.spring.colegio.funcionarios.controller;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.service.impl.ProfessorServiceImpl;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/professores")
@RequiredArgsConstructor
@Tag(name = "Professores", description = "Gerenciamento de corpo docente")
@CrossOrigin(origins = "*")
public class ProfessorController {

    private final ProfessorServiceImpl service;

    @PostMapping
    @Operation(summary = "Criar Professor")
    public ResponseEntity<Professor> create(@RequestBody @Valid ProfessorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProfessor(dto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar Professor")
    public ResponseEntity<Professor> update(@PathVariable Long id, @RequestBody ProfessorDTO dto) {
        return ResponseEntity.ok(service.updateProfessor(id, dto));
    }

    @GetMapping
    @Operation(summary = "Listar Professores")
    public ResponseEntity<List<Professor>> findAll() {
        return ResponseEntity.ok(service.findAllProfessores());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Professor por ID")
    public ResponseEntity<Professor> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findProfessorById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover Professor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Vincular Aluno a Professor", description = "Cria o relacionamento entre um professor e um aluno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor ou Aluno não encontrados")
    })
    @PostMapping("/{idProfessor}/alunos/{idAluno}")
    public ResponseEntity<Void> addAluno(@PathVariable Long idProfessor, @PathVariable Long idAluno) {
        service.addAlunoToProfessor(idProfessor, idAluno);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desvincular Aluno de Professor", description = "Remove o relacionamento entre um professor e um aluno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor ou Aluno não encontrados")
    })
    @DeleteMapping("/{idProfessor}/alunos/{idAluno}")
    public ResponseEntity<Void> removeAluno(@PathVariable Long idProfessor, @PathVariable Long idAluno) {
        service.removeAlunoFromProfessor(idProfessor, idAluno);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar Alunos de um Professor", description = "Retorna todos os alunos vinculados a um professor.")
    @GetMapping("/{idProfessor}/alunos")
    public ResponseEntity<Set<Aluno>> getAlunosByProfessor(@PathVariable Long idProfessor) {
        return ResponseEntity.ok(service.findAlunosByProfessor(idProfessor));
    }
}