package br.com.mba.spring.colegio.usuarios.dto;

import br.com.mba.spring.colegio.usuarios.enums.Genero;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UsuarioDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UsuarioDTO criarDtoValido() {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01000-000");

        UsuarioDTO dto = new UsuarioDTO();
        dto.setTipoUsuario(TipoUsuario.ALUNO);
        dto.setNome("João da Silva");
        dto.setDataNascimento(LocalDate.of(1990, 1, 1));
        dto.setCpf("123.456.789-00");
        dto.setMatricula("MAT-123");

        dto.setTelefone("11999998888");

        dto.setEmail("joao@email.com");
        dto.setGenero(Genero.MASCULINO);
        dto.setEndereco(endereco);
        return dto;
    }

    @Test
    @DisplayName("Deve validar DTO com sucesso quando todos os campos estão corretos")
    void deveValidarDtoCorreto() {
        UsuarioDTO dto = criarDtoValido();
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar quando email é inválido")
    void deveFalharEmailInvalido() {
        UsuarioDTO dto = criarDtoValido();
        dto.setEmail("email-invalido"); // Sem @ ou domínio

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("email"));
    }

    @Test
    @DisplayName("Deve falhar quando CPF tem formato incorreto")
    void deveFalharCpfInvalido() {
        UsuarioDTO dto = criarDtoValido();
        dto.setCpf("123456"); // Tamanho errado

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
        // Ajuste aqui para verificar a mensagem exata ou o campo
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("cpf"));
    }

    @Test
    @DisplayName("Deve falhar quando Data de Nascimento é futura")
    void deveFalharDataNascimentoFutura() {
        UsuarioDTO dto = criarDtoValido();
        dto.setDataNascimento(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("passado"));
    }

}
