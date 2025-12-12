package br.com.mba.spring.colegio.usuarios.repository;

import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.model.Nota;
import br.com.mba.spring.colegio.disciplinas.repository.DisciplinaRepository;
import br.com.mba.spring.colegio.disciplinas.repository.NotaRepository;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.repository.ProfessorRepository;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.AlunoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotaRepositoryTest {

    @Autowired private NotaRepository notaRepository;
    @Autowired private AlunoRepository alunoRepository;
    @Autowired private DisciplinaRepository disciplinaRepository;
    @Autowired private ProfessorRepository professorRepository;

    @Test
    @DisplayName("Deve salvar nota para um aluno em uma disciplina")
    void deveSalvarNota() {
        // 1. Preparar dependências (Professor -> Disciplina)
        Professor prof = professorRepository.save(Professor.builder()
                .nome("Prof Nota")
                .cpf("888.888.888-88")
                .especialidade("Matemática")
                .build());

        Disciplina disciplina = disciplinaRepository.save(Disciplina.builder()
                .nome("Cálculo I")
                .cargaHoraria(80)
                .professor(prof)
                .build());

        // 2. Preparar dependências (Usuario -> Aluno)
        Usuario usuario = Usuario.builder()
                .nome("Aluno Nota")
                .cpf("777.777.777-77")
                .email("aluno@nota.com")
                .matricula("MAT-NOTA")
                .tipoUsuario(TipoUsuario.ALUNO)
                // CORREÇÃO: Campos obrigatórios adicionados
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .telefone("11999999999")
                .build();

        Aluno aluno = Aluno.builder()
                .usuario(usuario)
                .nomeResponsavel("Mãe do Aluno") // Campo obrigatório do Aluno
                .sala("1A")
                .turno(br.com.mba.spring.colegio.usuarios.enums.Turno.MANHA)
                .build();

        Aluno alunoSalvo = alunoRepository.save(aluno);

        // 3. Ação: Salvar Nota
        Nota nota = Nota.builder()
                .aluno(alunoSalvo)
                .disciplina(disciplina)
                .valor(new BigDecimal("9.5"))
                .semestre(1)
                .build();

        Nota notaSalva = notaRepository.save(nota);

        // 4. Validação
        assertThat(notaSalva.getIdNota()).isNotNull();
        assertThat(notaSalva.getValor()).isEqualByComparingTo("9.5");
    }
}