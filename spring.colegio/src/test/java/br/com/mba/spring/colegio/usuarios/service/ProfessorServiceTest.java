package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.mapper.ProfessorMapper;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.repository.ProfessorRepository;
import br.com.mba.spring.colegio.funcionarios.service.ProfessorService;
import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateResourceException; // <--- Import corrigido
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService service;

    @Mock
    private ProfessorRepository repository;

    @Mock
    private ProfessorMapper mapper;

    @Test
    @DisplayName("Deve criar professor com sucesso")
    void deveCriarProfessor() {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setCpf("123.456.789-00");

        Professor professor = new Professor();

        when(repository.existsByCpf(dto.getCpf())).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(professor);
        when(repository.save(professor)).thenReturn(professor);

        service.createProfessor(dto);

        verify(repository).save(professor);
    }

    @Test
    @DisplayName("Deve lançar erro ao duplicar CPF")
    void deveFalharCpfDuplicado() {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setCpf("123.456.789-00");

        // Simula que o CPF já existe
        when(repository.existsByCpf(dto.getCpf())).thenReturn(true);

        // CORREÇÃO: Esperar DuplicateResourceException em vez de DuplicateCpfException
        assertThrows(DuplicateResourceException.class, () -> service.createProfessor(dto));

        verify(repository, never()).save(any());
    }
}