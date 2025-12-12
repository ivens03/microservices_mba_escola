package br.com.mba.spring.colegio.disciplinas.repository;

import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.model.Nota;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {
    boolean existsByAlunoAndDisciplinaAndSemestre(Aluno aluno, Disciplina disciplina, Integer semestre);
    List<Nota> findByAluno_IdAluno(Long idAluno);
}