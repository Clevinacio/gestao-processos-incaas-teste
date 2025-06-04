package com.incaas.api.gestorprocessos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.EnumStatus;

@Repository
public interface ProcessoJudicialRepository extends JpaRepository<ProcessoJudicial, Long> {
    ProcessoJudicial findByNumeroProcesso(String numeroProcesso);
    List<ProcessoJudicial> findByStatusAndComarca(EnumStatus status, String comarca);
    List<ProcessoJudicial> findByStatus(EnumStatus status);
    List<ProcessoJudicial> findByComarca(String comarca);
}
