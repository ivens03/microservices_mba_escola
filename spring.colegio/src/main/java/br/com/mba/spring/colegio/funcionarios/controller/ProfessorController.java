package br.com.mba.spring.colegio.funcionarios.controller;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.service.impl.ProfessorServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}