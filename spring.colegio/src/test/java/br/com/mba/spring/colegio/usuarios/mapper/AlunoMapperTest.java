package br.com.mba.spring.colegio.usuarios.mapper;

import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.enums.Turno;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class AlunoMapperTest {

    @InjectMocks
    private AlunoMapper alunoMapper;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    @DisplayName("Deve converter DTO para Entidade")
    void toEntity() {
        UsuarioDTO userDto = new UsuarioDTO();
        AlunoDTO alunoDto = new AlunoDTO();
        alunoDto.setDadosPessoais(userDto);
        alunoDto.setSala("1B");
        alunoDto.setTurno(Turno.INTEGRAL);

        Usuario usuarioMock = new Usuario();
        when(usuarioMapper.toEntity(userDto)).thenReturn(usuarioMock);

        Aluno result = alunoMapper.toEntity(alunoDto);

        assertNotNull(result);
        assertEquals("1B", result.getSala());
        assertEquals(Turno.INTEGRAL, result.getTurno());
        assertEquals(usuarioMock, result.getUsuario());
    }

    @Test
    @DisplayName("Deve atualizar Entidade a partir do DTO")
    void updateEntity() {
        AlunoDTO dto = new AlunoDTO();
        dto.setSala("Nova Sala");
        dto.setDadosPessoais(new UsuarioDTO());

        Usuario usuarioAntigo = new Usuario();
        Aluno entidade = new Aluno();
        entidade.setUsuario(usuarioAntigo);
        entidade.setSala("Sala Antiga");

        alunoMapper.updateEntityFromDto(dto, entidade);

        assertEquals("Nova Sala", entidade.getSala());
        // Verifica se delegou a atualização do usuário para o outro mapper
        verify(usuarioMapper).updateEntityFromDto(dto.getDadosPessoais(), usuarioAntigo);
    }

}
