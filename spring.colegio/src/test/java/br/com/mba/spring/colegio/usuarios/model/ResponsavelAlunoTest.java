package br.com.mba.spring.colegio.usuarios.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponsavelAlunoTest {

    @Test
    @DisplayName("Builder: Deve construir objeto corretamente com composição")
    void testBuilder() {
        Usuario usuario = Usuario.builder().nome("Teste").build();

        ResponsavelAluno responsavel = ResponsavelAluno.builder()
                .usuario(usuario)
                .historicoAluno(Map.of("ano", 2024))
                .build();

        assertNotNull(responsavel.getUsuario());
        assertEquals("Teste", responsavel.getUsuario().getNome());
        assertEquals(2024, responsavel.getHistoricoAluno().get("ano"));
    }
}
