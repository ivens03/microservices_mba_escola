package br.com.mba.spring.colegio.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "DTO para operações de cadastro e atualização de Responsáveis")
public class ResponsavelAlunoDTO {

    @Valid
    @NotNull(message = "Dados pessoais do usuário são obrigatórios")
    private UsuarioDTO dadosPessoais;

    @Schema(description = "Histórico escolar ou observações", example = "{\"observacao\": \"Responsável legal desde 2020\"}")
    private Map<String, Object> historicoAluno;

    @Schema(description = "Dados de pagamento", example = "{\"metodo\": \"CARTAO\", \"vencimento\": 10}")
    private Map<String, Object> pagamento;

}
