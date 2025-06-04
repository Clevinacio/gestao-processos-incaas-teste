package com.incaas.api.gestorprocessos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incaas.api.gestorprocessos.model.Audiencia;

@Repository
public interface AudienciaRepository extends JpaRepository<Audiencia, Long> {
    @Query("SELECT a FROM Audiencia a WHERE a.dataHora = :dataHora AND a.vara = :vara AND a.local = :local")
    public List<Audiencia> findByProcessoAndLocal(LocalDateTime dataHora, String vara, String local);
}
