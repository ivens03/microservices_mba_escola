package br.com.mba.spring.colegio.disciplinas.dto;

import br.com.mba.spring.colegio.disciplinas.enums.MateriaObrigatoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Set;

@Data
public class DisciplinaDTO {

    @NotNull(message = "A matéria é obrigatória")
    @Schema(description = "Enum da matéria (ex: EF_MATEMATICA, EM_FISICA)", example = "EM_MATEMATICA")
    private MateriaObrigatoria materia;

    @NotNull(message = "Carga horária é obrigatória")
    @Positive
    @Schema(example = "80")
    private Integer cargaHoraria;

    @NotNull(message = "ID do professor é obrigatório")
    @Schema(example = "1")
    private Long idProfessor;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String nomeProfessor;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<Long> alunosMatriculadosIds;
}
