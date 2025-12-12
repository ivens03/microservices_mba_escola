package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.repository.DisciplinaRepository;
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
class DisciplinaRepositoryTest {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    @DisplayName("Deve salvar disciplina vinculada a professor")
    void deveSalvarDisciplina() {
        // Cenário: Criar professor primeiro (FK obrigatória)
        Professor professor = Professor.builder()
                .nome("Prof. Teste")
                .cpf("000.000.000-00")
                .especialidade("Geral")
                .build();
        Professor profSalvo = professorRepository.save(professor);

        Disciplina disciplina = Disciplina.builder()
                .nome("Algoritmos")
                .cargaHoraria(60)
                .professor(profSalvo)
                .build();

        // Ação
        Disciplina salva = disciplinaRepository.save(disciplina);

        // Verificação
        assertThat(salva.getIdDisciplina()).isNotNull();
        assertThat(salva.getProfessor().getIdProfessor()).isEqualTo(profSalvo.getIdProfessor());
    }
}
