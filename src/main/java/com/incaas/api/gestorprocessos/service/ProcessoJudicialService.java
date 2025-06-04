package com.incaas.api.gestorprocessos.service;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;

public interface ProcessoJudicialService {
    public ProcessoJudicial cadastrarProcesso(ProcessoJudicialDTO processoJudicialDTO);
    public ProcessoJudicial buscarProcessoPorId(Long id);
    public ProcessoJudicial atualizarProcesso(Long id, ProcessoJudicialDTO processoJudicialDTO);
}
