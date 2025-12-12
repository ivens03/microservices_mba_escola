package br.com.mba.spring.colegio.disciplinas.service.impl;

import br.com.mba.spring.colegio.disciplinas.dto.DisciplinaDTO;
import br.com.mba.spring.colegio.disciplinas.model.Disciplina;

import java.util.List;

public interface DisciplinaServiceImpl {
    Disciplina createDisciplina(DisciplinaDTO dto);
    Disciplina updateDisciplina(Long id, DisciplinaDTO dto);
    List<Disciplina> findAllDisciplinas();
    Disciplina findDisciplinaById(Long id);
    void deleteDisciplina(Long id);
}