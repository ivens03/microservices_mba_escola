package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.BusinessException;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.mapper.UsuarioMapper;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.UsuarioRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    @DisplayName("Create: Deve lançar exceção se CPF já existe")
    void createDeveFalharCpfExistente() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCpf("123.456.789-00");

        when(usuarioRepository.existsByCpf(dto.getCpf())).thenReturn(true);

        assertThrows(BusinessException.class, () -> usuarioService.create(dto));

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Create: Deve salvar usuário com sucesso")
    void createSucesso() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCpf("123.456.789-00");
        dto.setMatricula("MAT-123");

        Usuario usuarioMapeado = new Usuario();

        // Mocks
        when(usuarioRepository.existsByCpf(any())).thenReturn(false);
        when(usuarioRepository.existsByMatricula(any())).thenReturn(false);
        when(usuarioMapper.toEntity(dto)).thenReturn(usuarioMapeado);
        when(usuarioRepository.save(usuarioMapeado)).thenReturn(usuarioMapeado);

        Usuario resultado = usuarioService.create(dto);

        assertThat(resultado).isNotNull();
        verify(usuarioRepository).save(usuarioMapeado);
    }

    @Test
    @DisplayName("Update: Deve atualizar usuário existente")
    void updateSucesso() {
        Long id = 1L;
        UsuarioDTO dto = new UsuarioDTO();
        Usuario usuarioExistente = new Usuario();

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.update(id, dto);

        assertThat(resultado).isNotNull();
        verify(usuarioMapper).updateEntityFromDto(dto, usuarioExistente);
        verify(usuarioRepository).save(usuarioExistente);
    }

    @Test
    @DisplayName("Update: Deve lançar erro se usuário não existe")
    void updateFalhaNaoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> usuarioService.update(1L, new UsuarioDTO()));
    }
}