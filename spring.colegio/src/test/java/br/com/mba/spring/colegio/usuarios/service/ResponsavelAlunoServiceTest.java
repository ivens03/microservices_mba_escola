package br.com.mba.spring.colegio.usuarios.service;

import br.com.mba.spring.colegio.globalHandler.exeption.DuplicateCpfException;
import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import br.com.mba.spring.colegio.usuarios.mapper.ResponsavelAlunoMapper;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.repository.ResponsavelAlunoRepository;
import br.com.mba.spring.colegio.usuarios.service.impl.UsuarioServiceImpl;
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
class ResponsavelAlunoServiceTest {

    @InjectMocks
    private ResponsavelAlunoService service;

    @Mock
    private ResponsavelAlunoRepository repository;
    @Mock
    private UsuarioServiceImpl usuarioService;
    @Mock
    private ResponsavelAlunoMapper mapper;

    @Test
    @DisplayName("Deve criar responsável passando pelo UsuarioService")
    void deveCriarResponsavel() {
        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        UsuarioDTO userDto = new UsuarioDTO();
        userDto.setCpf("12345678900");
        dto.setDadosPessoais(userDto);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setTipoUsuario(TipoUsuario.RESPONSAVEIS_DE_ALUNO);

        ResponsavelAluno entity = new ResponsavelAluno();

        when(repository.existsByUsuario_Cpf(any())).thenReturn(false);
        when(usuarioService.createUsuario(any())).thenReturn(usuarioSalvo);
        when(mapper.toEntity(any())).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);

        service.createResponsavel(dto);

        // Verifica se chamou o serviço de usuário e garantiu o ENUM correto
        verify(usuarioService).createUsuario(argThat(u -> u.getTipoUsuario() == TipoUsuario.RESPONSAVEIS_DE_ALUNO));
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF duplicado no repositório de responsáveis")
    void deveFalharCpfDuplicado() {
        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        UsuarioDTO userDto = new UsuarioDTO();
        userDto.setCpf("11122233344");
        dto.setDadosPessoais(userDto);

        when(repository.existsByUsuario_Cpf("11122233344")).thenReturn(true);

        assertThrows(DuplicateCpfException.class, () -> service.createResponsavel(dto));

        verify(usuarioService, never()).createUsuario(any());
    }
}
