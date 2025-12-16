package br.com.mba.spring.colegio.funcionarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "DTO para Professor")
public class ProfessorDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Schema(example = "Alberto Einstein")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)|(^\\d{11}$)", message = "CPF inválido")
    @Schema(example = "123.456.789-00")
    private String cpf;

    @NotBlank(message = "Especialidade é obrigatória")
    @Schema(example = "Física Quântica")
    private String especialidade;

    @Schema(example = "Doutorado em Física")
    private String formacao;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Long> alunosIds;
}
