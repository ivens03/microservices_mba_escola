package br.com.mba.spring.colegio.usuarios.dto;

import br.com.mba.spring.colegio.usuarios.enums.Turno;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AlunoDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private AlunoDTO criarDtoValido() {
        EnderecoDTO end = new EnderecoDTO();
        end.setLogradouro("Rua");
        end.setNumero("1");
        end.setBairro("B");
        end.setCidade("C");
        end.setEstado("SP");
        end.setCep("00000-000");

        UsuarioDTO user = new UsuarioDTO();
        user.setNome("Aluno");
        user.setCpf("123.456.789-00");
        user.setEmail("a@a.com");
        user.setMatricula("123");
        user.setTelefone("11999998888");
        user.setDataNascimento(LocalDate.now().minusYears(10));
        user.setEndereco(end);

        AlunoDTO aluno = new AlunoDTO();
        aluno.setDadosPessoais(user);
        aluno.setNomeResponsavel("Responsavel");
        aluno.setSala("1A");
        aluno.setTurno(Turno.MANHA);

        return aluno;
    }

    @Test
    @DisplayName("Deve validar DTO completo corretamente")
    void dtoValido() {
        AlunoDTO dto = criarDtoValido();
        Set<ConstraintViolation<AlunoDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve falhar se Turno for nulo")
    void turnoNulo() {
        AlunoDTO dto = criarDtoValido();
        dto.setTurno(null);
        Set<ConstraintViolation<AlunoDTO>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("turno"));
    }

    @Test
    @DisplayName("Deve falhar se Dados Pessoais (Usuario) forem inválidos")
    void usuarioInvalidoAninhado() {
        AlunoDTO dto = criarDtoValido();
        dto.getDadosPessoais().setCpf("invalido"); // Erro dentro do objeto aninhado

        Set<ConstraintViolation<AlunoDTO>> violations = validator.validate(dto);
        assertThat(violations).isNotEmpty();
        // Verifica se o erro veio do caminho dadosPessoais.cpf
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().contains("dadosPessoais.cpf"));
    }

}
