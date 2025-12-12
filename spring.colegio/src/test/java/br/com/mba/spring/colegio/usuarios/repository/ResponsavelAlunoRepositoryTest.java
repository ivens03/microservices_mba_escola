package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ResponsavelAlunoRepositoryTest {

    @Autowired
    private ResponsavelAlunoRepository repository;

    @Test
    @DisplayName("Deve salvar Responsável e persistir Usuario em cascata")
    void deveSalvarComCascata() {
        Usuario usuario = Usuario.builder()
                .nome("Pai Teste")
                .cpf("999.888.777-66")
                .email("pai@teste.com")
                .matricula("RESP-01")
                .telefone("11999999999")
                .dataNascimento(LocalDate.of(1980, 1, 1))
                .tipoUsuario(TipoUsuario.RESPONSAVEIS_DE_ALUNO)
                .ativo(true)
                .status(true)
                .build();

        ResponsavelAluno responsavel = ResponsavelAluno.builder()
                .usuario(usuario)
                .historicoAluno(Map.of("obs", "teste"))
                .build();

        ResponsavelAluno salvo = repository.save(responsavel);

        assertThat(salvo.getIdResponsavel()).isNotNull();
        assertThat(salvo.getUsuario().getIdUsuario()).isNotNull();
        assertThat(repository.existsByUsuario_Cpf("999.888.777-66")).isTrue();
    }
}
