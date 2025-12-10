package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.usuarios.model.Endereco;
import br.com.mba.spring.colegio.usuarios.model.Genero;
import br.com.mba.spring.colegio.usuarios.model.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario criarUsuarioModelo() {
        Endereco endereco = Endereco.builder()
                .logradouro("Rua Real")
                .numero("100")
                .bairro("Centro")
                .cidade("Fortaleza")
                .estado("CE")
                .cep("60000-000")
                .build();

        return Usuario.builder()
                .nome("Teste DB")
                .cpf("000.000.000-01")
                .matricula("TEST-001")
                .email("teste@db.com")
                .telefone("85999990000")
                .dataNascimento(LocalDate.of(1995, 5, 5))
                .status(true)
                .genero(Genero.OUTRO)
                .endereco(endereco)
                .build();
    }

    @Test
    @DisplayName("Deve salvar e buscar usuário por ID")
    void deveSalvarEBuscarUsuario() {
        Usuario usuario = criarUsuarioModelo();
        Usuario salvo = usuarioRepository.save(usuario);

        assertThat(salvo.getIdUsuario()).isNotNull();
        assertThat(salvo.getEndereco().getIdEndereco()).isNotNull(); // Cascade check

        Optional<Usuario> encontrado = usuarioRepository.findById(salvo.getIdUsuario());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail()).isEqualTo("teste@db.com");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar CPF duplicado")
    void deveFalharCpfDuplicado() {
        Usuario u1 = criarUsuarioModelo();
        usuarioRepository.save(u1);

        Usuario u2 = criarUsuarioModelo();
        u2.setEmail("outro@email.com"); // Email diferente
        u2.setMatricula("TEST-002"); // Matricula diferente
        // CPF igual ao u1

        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.saveAndFlush(u2);
        });
    }

    @Test
    @DisplayName("Deve verificar existência por Email")
    void existsByEmail() {
        Usuario usuario = criarUsuarioModelo();
        usuarioRepository.save(usuario);

        boolean existe = usuarioRepository.existsByEmail("teste@db.com");
        assertThat(existe).isTrue();
    }
}