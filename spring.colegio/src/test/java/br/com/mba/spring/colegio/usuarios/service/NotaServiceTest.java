package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.disciplinas.dto.NotaDTO;
import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.disciplinas.model.Nota;
import br.com.mba.spring.colegio.disciplinas.repository.NotaRepository;
import br.com.mba.spring.colegio.disciplinas.service.NotaService;
import br.com.mba.spring.colegio.disciplinas.service.impl.DisciplinaServiceImpl;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.service.impl.AlunoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

    @InjectMocks
    private NotaService service;

    @Mock private NotaRepository repository;
    @Mock private AlunoServiceImpl alunoService;
    @Mock private DisciplinaServiceImpl disciplinaService;

    @Test
    @DisplayName("Deve lançar nota corretamente")
    void deveLancarNota() {
        NotaDTO dto = new NotaDTO();
        dto.setIdAluno(1L);
        dto.setIdDisciplina(10L);
        dto.setValor(new BigDecimal("8.0"));
        dto.setSemestre(1);

        when(alunoService.findAlunoById(1L)).thenReturn(new Aluno());
        when(disciplinaService.findDisciplinaById(10L)).thenReturn(new Disciplina());
        when(repository.save(any(Nota.class))).thenReturn(new Nota());

        service.lancharNota(dto);

        verify(repository).save(any(Nota.class));
    }
}
