package br.com.mba.spring.colegio.disciplinas.model;

import br.com.mba.spring.colegio.usuarios.model.Aluno;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "frequencias", schema = "disciplinas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_aluno", "id_disciplina", "dataAula"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Frequencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFrequencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_aluno")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_disciplina")
    private Disciplina disciplina;

    @Column(nullable = false)
    private LocalDate dataAula;

    @Column(nullable = false)
    private Boolean presente;
}