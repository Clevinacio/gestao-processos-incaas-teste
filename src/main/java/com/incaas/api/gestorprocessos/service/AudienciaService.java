package com.incaas.api.gestorprocessos.service;

import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.model.Audiencia;

public interface AudienciaService {
    Audiencia cadastrarAudiencia(Long idProcesso, AudienciaDTO audienciaDTO);
}
