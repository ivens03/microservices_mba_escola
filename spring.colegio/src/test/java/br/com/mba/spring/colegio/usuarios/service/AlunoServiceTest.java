package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.AlunoNotFoundException;
import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.mapper.AlunoMapper;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.AlunoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private AlunoMapper alunoMapper;

    @Test
    @DisplayName("Create: Deve criar aluno orquestrando UsuarioService")
    void createSucesso() {
        AlunoDTO dto = new AlunoDTO();
        dto.setDadosPessoais(new UsuarioDTO());

        Usuario usuarioSalvo = new Usuario();
        Aluno alunoMapeado = new Aluno();

        // Mocks
        // CORREÇÃO: create -> createUsuario
        when(usuarioService.createUsuario(any())).thenReturn(usuarioSalvo);
        when(alunoMapper.toEntity(dto)).thenReturn(alunoMapeado);
        when(alunoRepository.save(alunoMapeado)).thenReturn(alunoMapeado);

        // CORREÇÃO: create -> createAluno
        Aluno resultado = alunoService.createAluno(dto);

        assertThat(resultado).isNotNull();
        // CORREÇÃO: create -> createUsuario
        verify(usuarioService).createUsuario(dto.getDadosPessoais());
        verify(alunoRepository).save(alunoMapeado);
    }

    @Test
    @DisplayName("FindById: Deve lançar AlunoNotFoundException se não existir")
    void findByIdNaoEncontrado() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());

        // CORREÇÃO: findById -> findAlunoById
        assertThrows(AlunoNotFoundException.class, () -> alunoService.findAlunoById(99L));
    }

    @Test
    @DisplayName("Delete: Deve deletar se existir")
    void deleteSucesso() {
        when(alunoRepository.existsById(1L)).thenReturn(true);

        // CORREÇÃO: delete -> deleteAluno
        alunoService.deleteAluno(1L);

        verify(alunoRepository).deleteById(1L);
    }
}