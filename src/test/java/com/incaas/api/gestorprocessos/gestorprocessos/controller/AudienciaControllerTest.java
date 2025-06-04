package com.incaas.api.gestorprocessos.gestorprocessos.controller;

import com.incaas.api.gestorprocessos.controller.AudienciaController;
import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.model.Audiencia;
import com.incaas.api.gestorprocessos.model.enums.EnumTipoAudiencia;
import com.incaas.api.gestorprocessos.service.AudienciaService;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AudienciaController.class)
class AudienciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AudienciaService audienciaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarAudiencia() throws Exception {
        Long idProcesso = 1L;
        AudienciaDTO audienciaDTO = new AudienciaDTO();
        audienciaDTO.setDataHora(LocalDateTime.parse("2024-06-10T10:00:00"));
        audienciaDTO.setLocal("Sala 1");
        audienciaDTO.setTipo(EnumTipoAudiencia.AUDIENCIA_INSTRUCAO);
        Audiencia audiencia = new Audiencia();

        Mockito.when(audienciaService.cadastrarAudiencia(eq(idProcesso), any(AudienciaDTO.class))).thenReturn(audiencia);

        mockMvc.perform(post("/api/v1/processos/audiencias/{idProcesso}/agendar", idProcesso)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(audienciaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveRetornarListaDeAudiencias() throws Exception {
        String data = "2024-06-01";
        String comarca = "ComarcaX";
        Audiencia audiencia = new Audiencia();
        List<Audiencia> audiencias = List.of(audiencia);

        Mockito.when(audienciaService.buscarAudienciasPorDataEComarca(data, comarca)).thenReturn(audiencias);

        mockMvc.perform(get("/api/v1/processos/audiencias/buscar")
                .param("data", data)
                .param("comarca", comarca))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void deveRetornarListaVaziaDeAudiencias() throws Exception {
        String data = "2024-06-01";
        String comarca = "ComarcaY";

        Mockito.when(audienciaService.buscarAudienciasPorDataEComarca(data, comarca)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/processos/audiencias/buscar")
                .param("data", data)
                .param("comarca", comarca))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void deveRealizarBuscaDeAudienciasSemParametros() throws Exception {
        Mockito.when(audienciaService.buscarAudienciasPorDataEComarca(null, null)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/processos/audiencias/buscar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void deveRetornarBadRequestQuandoBodyInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/processos/audiencias/1/agendar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}