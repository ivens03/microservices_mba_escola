package br.com.mba.spring.colegio.usuarios.controller;

import br.com.mba.spring.colegio.funcionarios.controller.ProfessorController;
import br.com.mba.spring.colegio.funcionarios.dto.ProfessorDTO;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.funcionarios.service.impl.ProfessorServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfessorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProfessorServiceImpl service;

    @Test
    @DisplayName("POST /api/professores - Deve criar professor (201)")
    void createProfessor() throws Exception {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setNome("Teste");
        dto.setCpf("123.456.789-00");
        dto.setEspecialidade("Matemática");

        Professor professorSalvo = new Professor();
        professorSalvo.setIdProfessor(1L);

        when(service.createProfessor(any())).thenReturn(professorSalvo);

        mockMvc.perform(post("/api/professores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
