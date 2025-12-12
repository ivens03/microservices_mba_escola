package br.com.mba.spring.colegio.usuarios.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponsavelAlunoDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Deve falhar se 'dadosPessoais' for nulo")
    void validarDadosPessoaisObrigatorios() {
        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        dto.setDadosPessoais(null); // Campo obrigatório

        Set<ConstraintViolation<ResponsavelAlunoDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Dados pessoais do usuário são obrigatórios"));
    }

    @Test
    @DisplayName("Deve validar erro dentro do DTO aninhado (UsuarioDTO)")
    void validarUsuarioDtoAninhado() {
        ResponsavelAlunoDTO dto = new ResponsavelAlunoDTO();
        UsuarioDTO userInvalido = new UsuarioDTO();
        userInvalido.setCpf("123"); // CPF inválido (tamanho/formato)
        userInvalido.setEmail("email-errado"); // Email inválido
        dto.setDadosPessoais(userInvalido);

        Set<ConstraintViolation<ResponsavelAlunoDTO>> violations = validator.validate(dto);

        // Verifica se capturou os erros internos do UsuarioDTO
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().contains("dadosPessoais.cpf"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().contains("dadosPessoais.email"));
    }
}
