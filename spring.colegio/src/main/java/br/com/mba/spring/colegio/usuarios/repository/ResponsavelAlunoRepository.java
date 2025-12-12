package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsavelAlunoRepository extends JpaRepository<ResponsavelAluno, Long> {
    boolean existsByUsuario_Cpf(String cpf);
    Optional<ResponsavelAluno> findByUsuario_Cpf(String cpf);
}
