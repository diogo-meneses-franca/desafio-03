package com.pbcompass.api_resultados.repository;

import com.pbcompass.api_resultados.entities.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
}