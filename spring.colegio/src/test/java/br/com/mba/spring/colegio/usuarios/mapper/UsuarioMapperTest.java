package br.com.mba.spring.colegio.usuarios.mapper;

import br.com.mba.spring.colegio.usuarios.dto.EnderecoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.model.Genero;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioMapperTest {

    private final UsuarioMapper mapper = new UsuarioMapper();

    @Test
    @DisplayName("Deve converter DTO para Entidade corretamente")
    void toEntity() {
        // Arrange
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Maria");
        dto.setEmail("maria@teste.com");
        dto.setGenero(Genero.FEMININO);
        dto.setDataNascimento(LocalDate.of(2000, 1, 1));

        EnderecoDTO endDto = new EnderecoDTO();
        endDto.setLogradouro("Rua A");
        endDto.setCidade("Cidade B");
        // ... preencher outros campos obrigatórios do endereço
        dto.setEndereco(endDto);

        // Act
        Usuario entity = mapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(dto.getNome(), entity.getNome());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getGenero(), entity.getGenero());
        assertEquals(dto.getEndereco().getLogradouro(), entity.getEndereco().getLogradouro());
        assertTrue(entity.getStatus()); // Verifica valor default
    }

    @Test
    @DisplayName("Deve atualizar entidade existente com dados do DTO")
    void updateEntityFromDto() {
        // Arrange
        Usuario entity = Usuario.builder()
                .nome("Antigo Nome")
                .email("antigo@email.com")
                .build();

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Novo Nome");
        dto.setEmail("novo@email.com");

        // Act
        mapper.updateEntityFromDto(dto, entity);

        // Assert
        assertEquals("Novo Nome", entity.getNome());
        assertEquals("novo@email.com", entity.getEmail());
    }

}
