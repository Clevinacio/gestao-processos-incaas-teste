package com.incaas.api.getorprocessos.service;

import org.springframework.stereotype.Service;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.getorprocessos.dto.ProcessoJudicialDTO;

@Service
public interface ProcessoJudicialService {
    public ProcessoJudicial cadastrarProcesso(ProcessoJudicialDTO processoJudicialDTO);
    public ProcessoJudicial buscarProcessoPorId(Long id);
    public ProcessoJudicial atualizarProcesso(Long id, ProcessoJudicialDTO processoJudicialDTO);
}
