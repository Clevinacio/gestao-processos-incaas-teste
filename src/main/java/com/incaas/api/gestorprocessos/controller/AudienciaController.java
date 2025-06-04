package com.incaas.api.gestorprocessos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.model.Audiencia;
import com.incaas.api.gestorprocessos.service.AudienciaService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



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

    @GetMapping("/buscar")
    public ResponseEntity<List<Audiencia>> buscarAudienciasPorDataEComarca(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String data,
            @RequestParam(required = false) String comarca) {
        List<Audiencia> audiencias = audienciaService.buscarAudienciasPorDataEComarca(data, comarca);
        if (audiencias.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }
        return ResponseEntity.ok(audiencias);
        
    }
    
}
