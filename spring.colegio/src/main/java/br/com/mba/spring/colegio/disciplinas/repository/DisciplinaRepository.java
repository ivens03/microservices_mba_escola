package br.com.mba.spring.colegio.disciplinas.repository;

import br.com.mba.spring.colegio.disciplinas.enums.MateriaObrigatoria;
import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByMateriaAndProfessor_IdProfessor(MateriaObrigatoria materia, Long idProfessor);
    List<Disciplina> findByMateria(MateriaObrigatoria materia);
}
