package br.com.mba.spring.colegio.usuarios.controller;

import br.com.mba.spring.colegio.globalHandler.exeption.AlunoNotFoundException;
import br.com.mba.spring.colegio.usuarios.dto.AlunoDTO;
import br.com.mba.spring.colegio.usuarios.dto.EnderecoDTO;
import br.com.mba.spring.colegio.usuarios.dto.UsuarioDTO;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import br.com.mba.spring.colegio.usuarios.enums.Turno;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import br.com.mba.spring.colegio.usuarios.service.impl.AlunoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AlunoServiceImpl alunoService;

    private AlunoDTO criarDtoCompleto() {
        UsuarioDTO u = new UsuarioDTO();
        u.setNome("Aluno Teste");
        u.setCpf("123.123.123-44");
        u.setEmail("aluno@teste.com");
        u.setMatricula("MAT-TESTE");
        u.setTelefone("11999998888");
        u.setDataNascimento(LocalDate.of(2010, 1, 1));

        // CORREÇÃO: Preenchendo o campo obrigatório
        u.setTipoUsuario(TipoUsuario.ALUNO);

        EnderecoDTO e = new EnderecoDTO();
        e.setLogradouro("Rua"); e.setNumero("1"); e.setBairro("B");
        e.setCidade("C"); e.setEstado("SP"); e.setCep("00000-000");
        u.setEndereco(e);

        AlunoDTO dto = new AlunoDTO();
        dto.setDadosPessoais(u);
        dto.setNomeResponsavel("Responsavel");
        dto.setSala("3B");
        dto.setTurno(Turno.MANHA);
        return dto;
    }

    @Test
    @DisplayName("POST /api/alunos - Sucesso (201)")
    void createAluno() throws Exception {
        AlunoDTO dto = criarDtoCompleto();
        Aluno alunoSalvo = new Aluno();
        alunoSalvo.setIdAluno(1L);
        alunoSalvo.setSala(dto.getSala());

        when(alunoService.createAluno(any(AlunoDTO.class))).thenReturn(alunoSalvo);

        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAluno").value(1));
    }

    @Test
    @DisplayName("GET /api/alunos/{id} - 404 Not Found")
    void getAlunoNotFound() throws Exception {
        when(alunoService.findAlunoById(99L)).thenThrow(new AlunoNotFoundException("Não achei"));

        mockMvc.perform(get("/api/alunos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Aluno não encontrado"));
    }

    @Test
    @DisplayName("PUT /api/alunos/{id} - Sucesso (200)")
    void updateAluno() throws Exception {
        AlunoDTO dto = criarDtoCompleto();
        Aluno alunoAtualizado = new Aluno();
        alunoAtualizado.setIdAluno(1L);
        alunoAtualizado.setSala("Nova Sala");

        when(alunoService.updateAluno(eq(1L), any(AlunoDTO.class))).thenReturn(alunoAtualizado);

        mockMvc.perform(put("/api/alunos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sala").value("Nova Sala"));
    }

    @Test
    @DisplayName("DELETE /api/alunos/{id} - Sucesso (204)")
    void deleteAluno() throws Exception {
        mockMvc.perform(delete("/api/alunos/1"))
                .andExpect(status().isNoContent());
    }
}