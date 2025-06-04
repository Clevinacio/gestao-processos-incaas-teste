package com.incaas.api.gestorprocessos.gestorprocessos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incaas.api.gestorprocessos.controller.ProcessoJudicialController;
import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;
import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.StatusEnum;
import com.incaas.api.gestorprocessos.service.ProcessoJudicialService;

@WebMvcTest(ProcessoJudicialController.class)
class ProcessoJudicialControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private ProcessoJudicialService processoJudicialService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarProcesso() throws Exception {
         ProcessoJudicialDTO dto = new ProcessoJudicialDTO();
        dto.setNumeroProcesso("1234567-89.2023.1.10.0001");
        dto.setVara("1ª Vara");
        dto.setComarca("São Paulo");
        dto.setAssunto("Teste");
        dto.setStatus("ATIVO");

        ProcessoJudicial salvo = new ProcessoJudicial();
        salvo.setId(1L);
        salvo.setNumeroProcesso(dto.getNumeroProcesso());
        salvo.setVara(dto.getVara());
        salvo.setComarca(dto.getComarca());
        salvo.setAssunto(dto.getAssunto());
        salvo.setStatus(StatusEnum.ATIVO);

        when(processoJudicialService.cadastrarProcesso(any(ProcessoJudicialDTO.class))).thenReturn(salvo);
        mockMvc.perform(post("/api/v1/processos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numeroProcesso").value("1234567-89.2023.1.10.0001"))
                .andExpect(jsonPath("$.vara").value("1ª Vara"))
                .andExpect(jsonPath("$.comarca").value("São Paulo"))
                .andExpect(jsonPath("$.assunto").value("Teste"))
                .andExpect(jsonPath("$.status").value("ATIVO"));
    }

    @Test
    void deveRetornarBadRequestParaProcessoInvalido() throws Exception {
        ProcessoJudicialDTO dto = new ProcessoJudicialDTO();
        dto.setNumeroProcesso("1234567-89.2023.1.10.0001");
        dto.setVara("");
        dto.setComarca("São Paulo");
        dto.setAssunto("Teste");
        dto.setStatus("ATIVO");

        mockMvc.perform(post("/api/v1/processos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
        
        verify(processoJudicialService, never()).cadastrarProcesso(any(ProcessoJudicialDTO.class));
    }

    @Test
    void deveRetornarBadRequestParaNumeroProcessoInvalido() throws Exception {
        ProcessoJudicialDTO dto = new ProcessoJudicialDTO();
        dto.setNumeroProcesso("12345");
        dto.setVara("1ª Vara");
        dto.setComarca("São Paulo");
        dto.setAssunto("Teste");
        dto.setStatus("ATIVO");

        mockMvc.perform(post("/api/v1/processos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
        
        verify(processoJudicialService, never()).cadastrarProcesso(any(ProcessoJudicialDTO.class));
    }

    @Test
    void deveRetornarBadRequestParaNumeroProcessoVazio() throws Exception {
        ProcessoJudicialDTO dto = new ProcessoJudicialDTO();
        dto.setNumeroProcesso("");
        dto.setVara("1ª Vara");
        dto.setComarca("São Paulo");
        dto.setAssunto("Teste");
        dto.setStatus("ATIVO");

        mockMvc.perform(post("/api/v1/processos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
        
        verify(processoJudicialService, never()).cadastrarProcesso(any(ProcessoJudicialDTO.class));
    }

    @Test
    void deveRetornarJsonVazioParaListaDeProcessos() throws Exception {
        when(processoJudicialService.listarProcessos(null, null)).thenReturn(List.of());
        
        mockMvc.perform(get("/api/v1/processos")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        
        verify(processoJudicialService, times(1)).listarProcessos(null, null);
    }

    @Test 
    void deveRetornarJsonComProcessos() throws Exception {
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setId(1L);
        processo.setNumeroProcesso("1234567-89.2023.1.10.0001");
        processo.setVara("1ª Vara");
        processo.setComarca("São Paulo");
        processo.setAssunto("Teste");
        processo.setStatus(StatusEnum.ATIVO);

        when(processoJudicialService.listarProcessos(null, null)).thenReturn(List.of(processo));
        
        mockMvc.perform(get("/api/v1/processos")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].numeroProcesso").value("1234567-89.2023.1.10.0001"))
                .andExpect(jsonPath("$[0].vara").value("1ª Vara"))
                .andExpect(jsonPath("$[0].comarca").value("São Paulo"))
                .andExpect(jsonPath("$[0].assunto").value("Teste"))
                .andExpect(jsonPath("$[0].status").value("ATIVO"));
        
        verify(processoJudicialService, times(1)).listarProcessos(null, null);
    }

    @Test
    void deveRetornarProcessosPorStatusEComarca() throws Exception {
        String status = "ATIVO";
        String comarca = "São Paulo";

        when((processoJudicialService.listarProcessos(status, comarca))).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/processos")
                .param("status", status)
                .param("comarca", comarca)
                .contentType("application/json"))
                .andExpect(status().isOk());
        
        verify(processoJudicialService, times(1)).listarProcessos(status, comarca);
    }
}