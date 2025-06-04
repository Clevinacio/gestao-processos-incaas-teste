package com.incaas.api.getorprocessos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;

public interface ProcessoJudicialRepository extends JpaRepository<ProcessoJudicial, Long> {

}
