package br.com.mba.spring.colegio.usuarios.controller;

import br.com.mba.spring.colegio.globalHandler.exeption.ResponsavelAlunoNotFoundException;
import br.com.mba.spring.colegio.usuarios.dto.EnderecoDTO;
import br.com.mba.spring.colegio.usuarios.dto.ResponsavelAlunoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import br.com.mba.spring.colegio.usuarios.model.ResponsavelAluno;
import br.com.mba.spring.colegio.usuarios.service.impl.ResponsavelAlunoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResponsavelAlunoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ResponsavelAlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ResponsavelAlunoServiceImpl service;

    private ResponsavelAlunoDTO criarDtoValido() {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setLogradouro("Rua A");
        endereco.setNumero("10");
        endereco.setBairro("Centro");
        endereco.setCidade("Fortaleza");
        endereco.setEstado("CE");
        endereco.setCep("60000-000");

        UsuarioDTO userDto = new UsuarioDTO();
        userDto.setNome("Responsavel Teste");
        userDto.setCpf("123.456.789-00");
        userDto.setMatricula("RESP-2025");
        userDto.setTelefone("85999999999");
        userDto.setEmail("resp@teste.com");
        userDto.setDataNascimento(LocalDate.of(1980, 1, 1));
        userDto.setEndereco(endereco);
        userDto.setTipoUsuario(TipoUsuario.RESPONSAVEIS_DE_ALUNO);

        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        dto.setDadosPessoais(userDto);
        dto.setHistoricoAluno(Map.of("obs", "Sem restrições"));
        dto.setPagamento(Map.of("metodo", "PIX"));

        return dto;
    }

    @Test
    @DisplayName("POST /responsaveis - Sucesso (201 Created)")
    void createResponsavel() throws Exception {
        ResponsavelAlunoDTO dto = criarDtoValido();

        ResponsavelAluno responsavelSalvo = new ResponsavelAluno();
        responsavelSalvo.setIdResponsavel(1L);

        when(service.createResponsavel(any(ResponsavelAlunoDTO.class))).thenReturn(responsavelSalvo);

        mockMvc.perform(post("/responsaveis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idResponsavel").value(1));
    }

    @Test
    @DisplayName("GET /responsaveis/{id} - Erro 404 se não existir")
    void getResponsavelNotFound() throws Exception {
        when(service.findResponsavelById(99L))
                .thenThrow(new ResponsavelAlunoNotFoundException("Responsável não encontrado"));

        mockMvc.perform(get("/responsaveis/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /responsaveis/{id} - Sucesso na atualização parcial")
    void updateResponsavel() throws Exception {
        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        dto.setHistoricoAluno(Map.of("status", "Atualizado"));

        ResponsavelAluno entityAtualizada = new ResponsavelAluno();
        entityAtualizada.setIdResponsavel(1L);

        when(service.updateResponsavel(eq(1L), any(ResponsavelAlunoDTO.class))).thenReturn(entityAtualizada);

        mockMvc.perform(patch("/responsaveis/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /responsaveis/{id} - Sucesso na exclusão lógica (204 No Content)")
    void deleteResponsavel() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/responsaveis/{id}", id))
                .andExpect(status().isNoContent()); // Espera status 204

        verify(service, times(1)).deleteResponsavel(id);
    }
}