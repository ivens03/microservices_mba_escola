package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.usuarios.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByUsuario_IdUsuario(Long idUsuario);
}
