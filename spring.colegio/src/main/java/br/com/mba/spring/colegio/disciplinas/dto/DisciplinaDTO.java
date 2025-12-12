package br.com.mba.spring.colegio.disciplinas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DisciplinaDTO {

    @NotBlank(message = "Nome da disciplina é obrigatório")
    @Schema(example = "Matemática Avançada")
    private String nome;

    @NotNull(message = "Carga horária é obrigatória")
    @Positive
    @Schema(example = "60")
    private Integer cargaHoraria;

    @NotNull(message = "ID do professor é obrigatório")
    @Schema(example = "1")
    private Long idProfessor;
}
