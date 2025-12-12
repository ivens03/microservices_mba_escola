package br.com.mba.spring.colegio.disciplinas.model;

import br.com.mba.spring.colegio.usuarios.model.Aluno;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "notas", schema = "disciplinas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_aluno", "id_disciplina", "semestre"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNota;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer semestre;
}
