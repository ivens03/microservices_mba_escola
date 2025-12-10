package br.com.mba.spring.colegio.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alunos", schema = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAluno;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    private String nomeResponsavel;
    private String sala;
    private String turno;

}
