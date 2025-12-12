package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.repository.ProfessorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfessorRepositoryTest {

    @Autowired
    private ProfessorRepository repository;

    @Test
    @DisplayName("Deve salvar professor com sucesso")
    void deveSalvarProfessor() {
        Professor professor = Professor.builder()
                .nome("Alberto Einstein")
                .cpf("111.222.333-44")
                .especialidade("Física")
                .build();

        Professor salvo = repository.save(professor);

        assertThat(salvo.getIdProfessor()).isNotNull();
        assertThat(salvo.getCpf()).isEqualTo("111.222.333-44");
    }

    @Test
    @DisplayName("Deve verificar se CPF existe")
    void deveVerificarExistenciaCpf() {
        Professor professor = Professor.builder()
                .nome("Marie Curie")
                .cpf("999.888.777-66")
                .especialidade("Química")
                .build();
        repository.save(professor);

        boolean existe = repository.existsByCpf("999.888.777-66");

        assertThat(existe).isTrue();
    }
}
