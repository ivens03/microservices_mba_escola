package br.com.mba.spring.colegio.funcionarios.model;

import br.com.mba.spring.colegio.disciplinas.model.Disciplina;
import br.com.mba.spring.colegio.usuarios.model.Aluno;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "professores", schema = "funcionarios",
        uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidade Professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfessor;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(nullable = false)
    private String especialidade;

    private String formacao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "professor_aluno",
            schema = "usuarios",
            joinColumns = @JoinColumn(name = "id_professor"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    @JsonIgnoreProperties("professores")
    @Builder.Default
    private Set<Aluno> alunos = new HashSet<>();

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("professor")
    @Builder.Default
    private Set<Disciplina> disciplinas = new HashSet<>();
}
