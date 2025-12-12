package br.com.mba.spring.colegio.disciplinas.repository;

import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByNome(String nome);
}
