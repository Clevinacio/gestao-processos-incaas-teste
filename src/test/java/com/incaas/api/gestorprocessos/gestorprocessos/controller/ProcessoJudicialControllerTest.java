package com.incaas.api.gestorprocessos.gestorprocessos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incaas.api.gestorprocessos.controller.ProcessoJudicialController;
import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;
import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.EnumStatus;
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
        dto.setStatus(EnumStatus.ATIVO);

        ProcessoJudicial salvo = new ProcessoJudicial();
        salvo.setId(1L);
        salvo.setNumeroProcesso(dto.getNumeroProcesso());
        salvo.setVara(dto.getVara());
        salvo.setComarca(dto.getComarca());
        salvo.setAssunto(dto.getAssunto());
        salvo.setStatus(EnumStatus.ATIVO);

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
                .andExpect(jsonPath("$.status").value(EnumStatus.ATIVO.getStatus()));
    }

    @ParameterizedTest
    @MethodSource("invalidProcessoProvider")
    void deveRetornarBadRequestParaProcessoInvalido(ProcessoJudicialDTO dto) throws Exception {
        mockMvc.perform(post("/api/v1/processos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(processoJudicialService, never()).cadastrarProcesso(any(ProcessoJudicialDTO.class));
    }

    static Stream<Arguments> invalidProcessoProvider() {
        ProcessoJudicialDTO dto1 = new ProcessoJudicialDTO();
        dto1.setNumeroProcesso("1234567-89.2023.1.10.0001");
        dto1.setVara("");
        dto1.setComarca("São Paulo");
        dto1.setAssunto("Teste");
        dto1.setStatus(EnumStatus.ATIVO);

        ProcessoJudicialDTO dto2 = new ProcessoJudicialDTO();
        dto2.setNumeroProcesso("12345");
        dto2.setVara("1ª Vara");
        dto2.setComarca("São Paulo");
        dto2.setAssunto("Teste");
        dto2.setStatus(EnumStatus.ATIVO);

        ProcessoJudicialDTO dto3 = new ProcessoJudicialDTO();
        dto3.setNumeroProcesso("");
        dto3.setVara("1ª Vara");
        dto3.setComarca("São Paulo");
        dto3.setAssunto("Teste");
        dto3.setStatus(EnumStatus.ATIVO);

        return Stream.of(
            Arguments.of(dto1),
            Arguments.of(dto2),
            Arguments.of(dto3)
        );
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
        processo.setStatus(EnumStatus.ATIVO);

        when(processoJudicialService.listarProcessos(null, null)).thenReturn(List.of(processo));
        
        mockMvc.perform(get("/api/v1/processos")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].numeroProcesso").value("1234567-89.2023.1.10.0001"))
                .andExpect(jsonPath("$[0].vara").value("1ª Vara"))
                .andExpect(jsonPath("$[0].comarca").value("São Paulo"))
                .andExpect(jsonPath("$[0].assunto").value("Teste"))
                .andExpect(jsonPath("$[0].status").value(EnumStatus.ATIVO.getStatus()));
        
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