package com.pbcompass.apipropostas.repository;

import com.pbcompass.apipropostas.entities.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
}
