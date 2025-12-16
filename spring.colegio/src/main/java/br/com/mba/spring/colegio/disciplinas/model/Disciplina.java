package br.com.mba.spring.colegio.disciplinas.model;

import br.com.mba.spring.colegio.disciplinas.enums.MateriaObrigatoria;
import br.com.mba.spring.colegio.funcionarios.model.Professor;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "disciplinas", schema = "disciplinas",
        uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade Disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisciplina;

    // Substituímos 'String nome' pelo Enum para garantir consistência com o padrão BR
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MateriaObrigatoria materia;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", nullable = false)
    @JsonIgnoreProperties({"alunos", "disciplinas"})
    private Professor professor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "disciplina_aluno",
            schema = "disciplinas",
            joinColumns = @JoinColumn(name = "id_disciplina"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    @JsonIgnoreProperties({"professores", "disciplinas"})
    @Builder.Default
    private Set<Aluno> alunos = new HashSet<>();
}
