package br.com.mba.spring.colegio.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EnderecoDTO {

    @NotBlank(message = "Logradouro é obrigatório")
    @Schema(example = "Av. Paulista")
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    @Schema(example = "1578")
    private String numero;

    @Schema(example = "Apto 42")
    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    @Schema(example = "Bela Vista")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    @Schema(example = "São Paulo")
    private String cidade;

    @Size(min = 2, max = 2, message = "Estado deve ser a sigla (ex: SP)")
    @Schema(example = "SP")
    private String estado;

    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido (ex: 01310-200)")
    @Schema(example = "01310-200")
    private String cep;

}
