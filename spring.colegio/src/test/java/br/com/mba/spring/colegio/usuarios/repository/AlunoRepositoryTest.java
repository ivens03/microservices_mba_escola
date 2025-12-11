package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.usuarios.enums.Genero;
import br.com.mba.spring.colegio.usuarios.enums.Turno;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.Endereco;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    private Aluno criarAlunoModelo() {
        Endereco endereco = Endereco.builder()
                .logradouro("Rua Escola")
                .numero("10")
                .bairro("Centro")
                .cidade("São Paulo")
                .estado("SP")
                .cep("01000-000")
                .build();

        Usuario usuario = Usuario.builder()
                .nome("Aluno Teste")
                .cpf("111.222.333-44")
                .matricula("AL-2025")
                .email("aluno@escola.com")
                .telefone("11999998888")
                .dataNascimento(LocalDate.of(2010, 1, 1))
                .status(true)
                .genero(Genero.MASCULINO)
                .endereco(endereco)
                .build();

        return Aluno.builder()
                .usuario(usuario)
                .nomeResponsavel("Mãe do Aluno")
                .sala("5B")
                .turno(Turno.TARDE)
                .historicoNotas("{\"matematica\": 10}")
                .build();
    }

    @Test
    @DisplayName("Deve salvar Aluno e Cascata (Usuario/Endereco)")
    void deveSalvarAlunoComCascata() {
        Aluno aluno = criarAlunoModelo();
        Aluno salvo = alunoRepository.save(aluno);

        assertThat(salvo.getIdAluno()).isNotNull();
        assertThat(salvo.getUsuario().getIdUsuario()).isNotNull(); // Verifica Cascade
        assertThat(salvo.getUsuario().getEndereco().getIdEndereco()).isNotNull(); // Verifica Cascade Profundo
        assertThat(salvo.getTurno()).isEqualTo(Turno.TARDE);
    }

    @Test
    @DisplayName("Deve buscar Aluno pelo ID do Usuário")
    void deveBuscarPorIdUsuario() {
        Aluno aluno = criarAlunoModelo();
        Aluno salvo = alunoRepository.save(aluno);
        Long idUsuario = salvo.getUsuario().getIdUsuario();

        Optional<Aluno> encontrado = alunoRepository.findByUsuario_IdUsuario(idUsuario);

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNomeResponsavel()).isEqualTo("Mãe do Aluno");
    }

}
