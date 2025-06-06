package com.incaas.api.gestorprocessos.service;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;

import java.util.List;

import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;

public interface ProcessoJudicialService {
    public ProcessoJudicial cadastrarProcesso(ProcessoJudicialDTO processoJudicialDTO);
    public List<ProcessoJudicial> listarProcessos(String status, String comarca);
}
