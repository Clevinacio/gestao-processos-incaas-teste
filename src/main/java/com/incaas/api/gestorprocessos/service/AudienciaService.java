package com.incaas.api.gestorprocessos.service;

import java.util.List;

import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.model.Audiencia;

public interface AudienciaService {
    Audiencia cadastrarAudiencia(Long idProcesso, AudienciaDTO audienciaDTO);

    List<Audiencia> buscarAudienciasPorDataEComarca(String data, String comarca);
}
