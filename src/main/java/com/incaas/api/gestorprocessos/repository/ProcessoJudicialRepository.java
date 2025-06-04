package com.incaas.api.gestorprocessos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;

@Repository
public interface ProcessoJudicialRepository extends JpaRepository<ProcessoJudicial, Long> {
    Object findByNumeroProcesso(String numeroProcesso);
}
