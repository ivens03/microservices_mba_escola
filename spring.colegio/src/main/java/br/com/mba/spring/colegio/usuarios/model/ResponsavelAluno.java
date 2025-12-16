package br.com.mba.spring.colegio.usuarios.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "responsaveis_alunos", schema = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade Responsável pelo Aluno")
public class ResponsavelAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResponsavel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "Histórico do aluno vinculado em formato JSON")
    private Map<String, Object> historicoAluno;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    @Schema(description = "Dados de pagamento e financeiro em formato JSON")
    private Map<String, Object> pagamento;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "responsavel_aluno_vinculo",
            schema = "usuarios",
            joinColumns = @JoinColumn(name = "id_responsavel"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    @JsonIgnoreProperties("responsaveis")
    @Builder.Default
    private Set<Aluno> alunos = new HashSet<>();

}
