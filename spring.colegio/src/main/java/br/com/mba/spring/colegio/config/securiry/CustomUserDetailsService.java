package br.com.mba.spring.colegio.config.securiry;

import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        // Busca usuário pela MATRÍCULA
        Usuario usuario = usuarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com matrícula: " + matricula));

        // Retorna o objeto User do Spring Security
        return User.builder()
                .username(usuario.getMatricula()) // Login é a matrícula
                .password(usuario.getSenha())     // Senha é o hash do CPF salvo no banco
                .roles(usuario.getTipoUsuario().name()) // Perfil (ALUNO, PROFESSOR, etc)
                .disabled(!usuario.getAtivo())    // Bloqueia login se ativo = false
                .build();
    }

}
