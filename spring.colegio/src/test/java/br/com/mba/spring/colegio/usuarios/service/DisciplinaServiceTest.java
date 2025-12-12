package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.disciplinas.dto.DisciplinaDTO;
import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.repository.DisciplinaRepository;
import br.com.mba.spring.colegio.disciplinas.service.DisciplinaService;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.service.impl.ProfessorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisciplinaServiceTest {

    @InjectMocks
    private DisciplinaService service;

    @Mock
    private DisciplinaRepository repository;

    @Mock
    private ProfessorServiceImpl professorService;

    @Test
    @DisplayName("Deve criar disciplina com sucesso")
    void deveCriarDisciplina() {
        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setNome("Java");
        dto.setIdProfessor(1L);
        dto.setCargaHoraria(40);

        Professor professor = new Professor();
        professor.setIdProfessor(1L);

        when(professorService.findProfessorById(1L)).thenReturn(professor);
        when(repository.save(any(Disciplina.class))).thenReturn(new Disciplina());

        service.createDisciplina(dto);

        verify(repository).save(any(Disciplina.class));
    }
}
