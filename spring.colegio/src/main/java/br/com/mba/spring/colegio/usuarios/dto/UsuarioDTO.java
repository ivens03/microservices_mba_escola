package br.com.mba.spring.colegio.usuarios.dto;

import br.com.mba.spring.colegio.usuarios.model.Genero;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "DTO para criação e atualização de Usuários")
public class UsuarioDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    @Schema(example = "Carlos Silva")
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    @Schema(example = "1995-05-20", format = "date")
    private LocalDate dataNascimento;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)|(^\\d{11}$)", message = "CPF inválido")
    @Schema(example = "123.456.789-00")
    private String cpf;

    @NotBlank(message = "Matrícula é obrigatória")
    @Schema(example = "MAT-2025001")
    private String matricula;

    @NotNull(message = "Endereço é obrigatório")
    @Valid
    private EnderecoDTO endereco;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "Telefone deve seguir formato E.164 ou local")
    @Schema(example = "+5511999998888")
    private String telefone;

    @NotBlank(message = "Email é obrigatório")
    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Formato de email inválido")
    @Schema(example = "carlos@email.com")
    private String email;

    @Schema(example = "MASCULINO")
    private Genero genero;

    @Schema(example = "Mãe: (11) 98888-7777")
    private String contatoEmergencia;

}
