package com.incaas.api.gestorprocessos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.model.Audiencia;
import com.incaas.api.gestorprocessos.service.AudienciaService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/processos/audiencias")
public class AudienciaController {
    private final AudienciaService audienciaService;

    public AudienciaController(AudienciaService audienciaService) {
        this.audienciaService = audienciaService;
    }

    @PostMapping("{idProcesso}/agendar")
    public ResponseEntity<Audiencia> criarAudiencia(@PathVariable Long idProcesso, @Valid @RequestBody AudienciaDTO audenciaDTO) {
        Audiencia audiencia = audienciaService.cadastrarAudiencia(idProcesso, audenciaDTO);
        return ResponseEntity.ok(audiencia);
    }
}
