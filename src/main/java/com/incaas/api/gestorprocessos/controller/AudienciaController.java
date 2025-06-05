package com.incaas.api.gestorprocessos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.model.Audiencia;
import com.incaas.api.gestorprocessos.service.AudienciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("${api.base-path:/api}/${api.version:v1}/audiencias")
@Tag(name = "Audiências", description = "Endpoints para gestão de audiências de processos judiciais")
public class AudienciaController {
    private final AudienciaService audienciaService;

    public AudienciaController(AudienciaService audienciaService) {
        this.audienciaService = audienciaService;
    }

    @Operation(summary = "Agendar audiência para um processo judicial",
            description = "Cria uma nova audiência associada a um processo judicial existente.")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Audiência criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou processo não encontrado")
    })
    @PostMapping("{idProcesso}/agendar")
    public ResponseEntity<Audiencia> criarAudiencia(
        @Parameter(description = "ID do processo judicial ao qual a audiência será associada")
        @PathVariable Long idProcesso, @Valid @RequestBody AudienciaDTO audenciaDTO) {
        Audiencia audiencia = audienciaService.cadastrarAudiencia(idProcesso, audenciaDTO);
        return ResponseEntity.ok(audiencia);
    }

    @Operation(summary = "Listar audiências agendadas",
            description = "Retorna uma lista de audiências agendadas, podendo filtrar por data e comarca.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de audiências retornada com sucesso")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<Audiencia>> buscarAudienciasPorDataEComarca(
            @Parameter(description = "Data da audiência no formato yyyy-MM-dd")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String data,
            @Parameter(description = "Comarca da audiência")
            @RequestParam(required = false) String comarca) {
        List<Audiencia> audiencias = audienciaService.buscarAudienciasPorDataEComarca(data, comarca);
        if (audiencias.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }
        return ResponseEntity.ok(audiencias);
        
    }
    
}
