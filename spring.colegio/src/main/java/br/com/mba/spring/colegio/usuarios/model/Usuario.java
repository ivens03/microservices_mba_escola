package br.com.mba.spring.colegio.usuarios.model;

import br.com.mba.spring.colegio.usuarios.enums.Genero;
import br.com.mba.spring.colegio.usuarios.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios", schema = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "cpf"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "matricula")
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade Base de Usuário")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false)
    private String matricula;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "idEndereco")
    private Endereco endereco;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Builder.Default
    private Boolean status = true;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String contatoEmergencia;

    @Builder.Default
    @Column(nullable = false)
    @Schema(description = "Indica se o usuário está ativo no sistema", defaultValue = "true")
    private Boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    @Schema(description = "Perfil do usuário no sistema")
    private TipoUsuario tipoUsuario;

}
