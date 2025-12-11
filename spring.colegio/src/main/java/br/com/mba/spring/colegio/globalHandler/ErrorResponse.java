package br.com.mba.spring.colegio.globalHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto padrão para retorno de erros da API")
public class ErrorResponse {

    @Schema(description = "Data e hora do erro", example = "2025-12-10T21:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Código HTTP", example = "404")
    private int status;

    @Schema(description = "Tipo do erro", example = "Not Found")
    private String error;

    @Schema(description = "Mensagem detalhada", example = "Aluno não encontrado com o ID informado")
    private String message;

    @Schema(description = "Endpoint solicitado", example = "/api/alunos/1")
    private String path;

}
