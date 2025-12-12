package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByMatricula(String matricula);
    boolean existsByEmail(String email);
    Optional<Usuario> findByMatricula(String matricula);
}
