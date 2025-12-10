package br.com.mba.spring.colegio.usuarios.controller;

import br.com.mba.spring.colegio.exception.BusinessException;
import br.com.mba.spring.colegio.usuarios.dto.EnderecoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.model.Usuario;
import br.com.mba.spring.colegio.usuarios.service.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    private UsuarioDTO criarDtoValido() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Teste API");
        dto.setCpf("111.222.333-44");
        dto.setEmail("api@teste.com");
        dto.setMatricula("API-001");
        dto.setTelefone("11999999999");
        dto.setDataNascimento(java.time.LocalDate.of(2000, 1, 1));

        EnderecoDTO end = new EnderecoDTO();
        end.setLogradouro("Rua");
        end.setNumero("1");
        end.setBairro("Bairro");
        end.setCidade("Cidade");
        end.setEstado("SP");
        end.setCep("00000-000");
        dto.setEndereco(end);

        return dto;
    }

    @Test
    @DisplayName("POST /api/usuarios - Deve criar usuário e retornar 201")
    void deveCriarUsuario() throws Exception {
        UsuarioDTO dto = criarDtoValido();
        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setIdUsuario(1L);
        usuarioSalvo.setNome(dto.getNome());

        when(usuarioService.create(any(UsuarioDTO.class))).thenReturn(usuarioSalvo);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nome").value("Teste API"));
    }

    @Test
    @DisplayName("POST /api/usuarios - Deve retornar 400 se validação falhar")
    void deveRetornar400Validacao() throws Exception {
        UsuarioDTO dtoInvalido = new UsuarioDTO(); // Campos nulos

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/usuarios/{id} - Deve retornar usuário")
    void deveBuscarPorId() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNome("João");

        when(usuarioService.findById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @DisplayName("PATCH /api/usuarios/{id} - Deve atualizar usuário")
    void deveAtualizarUsuario() throws Exception {
        UsuarioDTO dto = criarDtoValido();
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setIdUsuario(1L);
        usuarioAtualizado.setNome(dto.getNome());

        when(usuarioService.update(eq(1L), any(UsuarioDTO.class))).thenReturn(usuarioAtualizado);

        mockMvc.perform(patch("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste API"));
    }

    @Test
    @DisplayName("DELETE /api/usuarios/{id} - Deve remover usuário")
    void deveDeletarUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Error Handler - Deve capturar BusinessException")
    void deveTratarErroNegocio() throws Exception {
        UsuarioDTO dto = criarDtoValido();

        // Simula erro no serviço (ex: CPF duplicado)
        when(usuarioService.create(any())).thenThrow(new BusinessException("CPF duplicado"));

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()) // Ou o status que você definiu no Handler
                .andExpect(jsonPath("$.error").value("Regra de Negócio"))
                .andExpect(jsonPath("$.message").value("CPF duplicado"));
    }

}
