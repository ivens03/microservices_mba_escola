package br.com.mba.spring.colegio.funcionarios.service.impl;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.usuarios.model.Aluno;

import java.util.List;
import java.util.Set;

public interface ProfessorServiceImpl {
    Professor createProfessor(ProfessorDTO dto);
    Professor updateProfessor(Long id, ProfessorDTO dto);
    List<Professor> findAllProfessores();
    Professor findProfessorById(Long id);
    void deleteProfessor(Long id);
    void addAlunoToProfessor(Long idProfessor, Long idAluno);
    void removeAlunoFromProfessor(Long idProfessor, Long idAluno);
    Set<Aluno> findAlunosByProfessor(Long idProfessor);
    Set<Professor> findProfessoresByAluno(Long idAluno);
}
