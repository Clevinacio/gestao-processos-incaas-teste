package com.incaas.api.gestorprocessos.controller;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;
import com.incaas.api.gestorprocessos.service.ProcessoJudicialService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/processos")
public class ProcessoJudicialController {
    private final ProcessoJudicialService processoJudicialService;

    public ProcessoJudicialController(ProcessoJudicialService processoJudicialService) {
        this.processoJudicialService = processoJudicialService;
    }

    @PostMapping
    public ResponseEntity<ProcessoJudicial> criarProcesso(@Valid @RequestBody ProcessoJudicialDTO processoDTO) {
        ProcessoJudicial processo = processoJudicialService.cadastrarProcesso(processoDTO);
        return ResponseEntity.ok(processo);
    }
    
}
