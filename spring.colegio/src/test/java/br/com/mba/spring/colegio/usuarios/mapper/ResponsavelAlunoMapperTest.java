package br.com.mba.spring.colegio.usuarios.mapper;

import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResponsavelAlunoMapperTest {

    @InjectMocks
    private ResponsavelAlunoMapper mapper;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    @DisplayName("toEntity: Deve converter DTO para Entidade chamando UsuarioMapper")
    void toEntity() {
        // Cenário
        UsuarioDTO userDto = new UsuarioDTO();
        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        dto.setDadosPessoais(userDto);
        dto.setHistoricoAluno(Map.of("chave", "valor"));

        Usuario usuarioMock = new Usuario();
        when(usuarioMapper.toEntity(userDto)).thenReturn(usuarioMock);

        // Ação
        ResponsavelAluno result = mapper.toEntity(dto);

        // Verificação
        assertNotNull(result);
        assertEquals(usuarioMock, result.getUsuario());
        assertEquals("valor", result.getHistoricoAluno().get("chave"));
    }

    @Test
    @DisplayName("updateEntityFromDto: Deve atualizar campos JSON e delegar update do Usuario")
    void updateEntity() {
        // Cenário
        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        dto.setPagamento(Map.of("status", "ok"));
        dto.setDadosPessoais(new UsuarioDTO());

        ResponsavelAluno entity = new ResponsavelAluno();
        Usuario usuarioEntity = new Usuario();
        entity.setUsuario(usuarioEntity);

        // Ação
        mapper.updateEntityFromDto(dto, entity);

        // Verificação
        assertEquals("ok", entity.getPagamento().get("status"));
        verify(usuarioMapper).updateEntityFromDto(dto.getDadosPessoais(), usuarioEntity);
    }
}
