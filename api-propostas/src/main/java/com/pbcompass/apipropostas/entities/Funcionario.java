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

        @EqualsAndHashCode.Include
        @Column(name = "id_funcionario", nullable = false, unique = true)
        private Long id;
        
        @Column(name = "nome_funcionario", nullable = false, length = 150)
        private String nome;

        @Column(name = "email_funcionario", nullable = false, length = 150)
        private String email;

}

