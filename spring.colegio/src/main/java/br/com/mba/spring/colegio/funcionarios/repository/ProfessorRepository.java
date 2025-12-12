package br.com.mba.spring.colegio.funcionarios.repository;

import br.com.mba.spring.colegio.funcionarios.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean existsByCpf(String cpf);
    Optional<Professor> findByCpf(String cpf);
}
