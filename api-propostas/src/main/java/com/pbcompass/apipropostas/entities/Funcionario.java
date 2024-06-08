package com.pbcompass.apipropostas.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class Funcionario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        private Long id;

        @Column(name = "nome", nullable = false, length = 150)
        private String nome;

        @Column(name = "email", nullable = false, length = 150)
        private String email;

}

