package br.com.mba.spring.colegio.usuarios.dto;

import br.com.mba.spring.colegio.usuarios.enums.Turno;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO para operações de cadastro e atualização de Alunos")
public class AlunoDTO {

    @Valid
    @NotNull(message = "Dados pessoais do usuário são obrigatórios")
    private UsuarioDTO dadosPessoais;

    @NotBlank(message = "Nome do responsável é obrigatório")
    @Schema(example = "Maria Silva")
    private String nomeResponsavel;

    @Schema(description = "JSON com histórico escolar", example = "{\"2024\": {\"matematica\": 9.5}}")
    private String historicoNotas;

    @NotBlank(message = "Sala é obrigatória")
    @Schema(example = "3B - Ensino Médio")
    private String sala;

    @NotNull(message = "Turno é obrigatório")
    @Schema(example = "MANHA")
    private Turno turno;

}
