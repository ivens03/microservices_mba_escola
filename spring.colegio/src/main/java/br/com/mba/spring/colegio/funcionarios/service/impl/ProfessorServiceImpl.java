package br.com.mba.spring.colegio.funcionarios.service.impl;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import java.util.List;

public interface ProfessorServiceImpl {
    Professor createProfessor(ProfessorDTO dto);
    Professor updateProfessor(Long id, ProfessorDTO dto);
    List<Professor> findAllProfessores();
    Professor findProfessorById(Long id);
    void deleteProfessor(Long id);
}
