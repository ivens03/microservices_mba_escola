package br.com.mba.spring.colegio.disciplinas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class NotaDTO {
    @NotNull private Long idAluno;
    @NotNull private Long idDisciplina;
    @NotNull @DecimalMin("0.0") @DecimalMax("10.0") private BigDecimal valor;
    @NotNull @Min(1) private Integer semestre;
}
