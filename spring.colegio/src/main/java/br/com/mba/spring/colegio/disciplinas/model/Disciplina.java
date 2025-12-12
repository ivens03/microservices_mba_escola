package br.com.mba.spring.colegio.disciplinas.model;

import br.com.mba.spring.colegio.funcionarios.model.Professor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "disciplinas", schema = "disciplinas",
        uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade Disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisciplina;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_professor", nullable = false)
    private Professor professor;
}
