package br.com.mba.spring.colegio.usuarios.model;

import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.usuarios.enums.Turno;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alunos", schema = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade Aluno (Especialização de Usuário)")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAluno;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nome_responsavel_legado")
    private String nomeResponsavel;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String historicoNotas;

    @Column(nullable = false)
    private String sala;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turno turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    @JsonIgnoreProperties("alunos")
    private ResponsavelAluno responsavel;

    @ManyToMany(mappedBy = "alunos", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("alunos")
    @Builder.Default
    private Set<Professor> professores = new HashSet<>();

    @ManyToMany(mappedBy = "alunos", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("alunos")
    @Builder.Default
    private Set<ResponsavelAluno> responsaveis = new HashSet<>();
}
