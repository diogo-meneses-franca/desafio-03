package com.pbcompass.apipropostas.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "votos")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "funcionario_id")
    private Long funcionarioId;

    @ManyToOne
    @JoinColumn(name = "proposta_id", nullable = false)
    private Proposta proposta;

    @Enumerated(EnumType.STRING)
    @Column(name = "decisao", nullable = false)
    private Decisao decisao;

    public enum Decisao{
        APROVAR,
        REJEITAR
    }
}
