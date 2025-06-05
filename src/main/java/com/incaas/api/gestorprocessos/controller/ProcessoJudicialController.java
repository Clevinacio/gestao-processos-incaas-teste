package com.incaas.api.gestorprocessos.controller;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;
import com.incaas.api.gestorprocessos.service.ProcessoJudicialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("${api.base-path:/api}/${api.version:v1}/processos")
@Tag(name = "Processos Judiciais", description = "Endpoints para gestão de processos judiciais")
public class ProcessoJudicialController {
    private final ProcessoJudicialService processoJudicialService;

    public ProcessoJudicialController(ProcessoJudicialService processoJudicialService) {
        this.processoJudicialService = processoJudicialService;
    }

    @Operation(summary = "Criar um novo processo judicial",
            description = "Cadastra um novo processo judicial no sistema.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Processo judicial criado com sucesso"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProcessoJudicial> criarProcesso(@Valid @RequestBody ProcessoJudicialDTO processoDTO) {
        ProcessoJudicial processo = processoJudicialService.cadastrarProcesso(processoDTO);
        return ResponseEntity.ok(processo);
    }

    @Operation(summary = "Listar processos judiciais",
            description = "Retorna uma lista de processos judiciais, podendo filtrar por status e comarca.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de processos retornada com sucesso"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nenhum processo encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ProcessoJudicial>> listar(@Parameter(description = "Status do processo atualmente") 
                                                         @RequestParam(required = false) String status,
                                                         @Parameter(description = "Comarca do processo atualmente")
                                                         @RequestParam(required = false) String comarca) {
        
        List<ProcessoJudicial> processos = processoJudicialService.listarProcessos(status, comarca);
        if (processos.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }
        return ResponseEntity.ok(processos);
    }
    
    
}
