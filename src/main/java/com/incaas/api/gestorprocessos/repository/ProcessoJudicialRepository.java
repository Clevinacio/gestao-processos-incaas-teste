package com.incaas.api.gestorprocessos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.StatusEnum;

@Repository
public interface ProcessoJudicialRepository extends JpaRepository<ProcessoJudicial, Long> {
    Object findByNumeroProcesso(String numeroProcesso);

    List<ProcessoJudicial> findByStatusAndComarca(StatusEnum status, String comarca);

    List<ProcessoJudicial> findByStatus(StatusEnum status);

    List<ProcessoJudicial> findByComarca(String comarca);
}
