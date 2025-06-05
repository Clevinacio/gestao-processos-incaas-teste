package com.incaas.api.gestorprocessos.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incaas.api.gestorprocessos.model.Audiencia;

@Repository
public interface AudienciaRepository extends JpaRepository<Audiencia, Long> {
    @Query("SELECT a FROM Audiencia a WHERE a.dataHora = :dataHora AND a.processo.vara = :vara AND a.local = :local")
    List<Audiencia> findByProcessoAndLocal(LocalDateTime dataHora, String vara, String local);

    @Query("SELECT a FROM Audiencia a WHERE a.processo.comarca = :comarca AND a.dataHora >= :inicio AND a.dataHora < :fim")
    List<Audiencia> findByComarcaAndDia(LocalDateTime inicio, LocalDateTime fim, String comarca);

    @Query("SELECT a FROM Audiencia a WHERE a.processo.comarca = :comarca")
    List<Audiencia> findByComarca(String comarca);

    @Query("SELECT a FROM Audiencia a WHERE a.dataHora >= :inicio AND a.dataHora < :fim")
    List<Audiencia> findByDia(LocalDateTime inicio, LocalDateTime fim, String comarca);
}
