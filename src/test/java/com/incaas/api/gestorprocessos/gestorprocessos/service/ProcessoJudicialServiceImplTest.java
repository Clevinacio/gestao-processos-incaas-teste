package com.incaas.api.gestorprocessos.gestorprocessos.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.Mockito.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;
import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.StatusEnum;
import com.incaas.api.gestorprocessos.repository.ProcessoJudicialRepository;
import com.incaas.api.gestorprocessos.service.ProcessoJudicialServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProcessoJudicialServiceImplTest {
    @InjectMocks
    private ProcessoJudicialServiceImpl processoJudicialService;

    @Mock
    private ProcessoJudicialRepository processoJudicialRepository;

    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        processoJudicialService = new ProcessoJudicialServiceImpl(processoJudicialRepository, modelMapper);
    }

    @Test
    void deveCriarProcesso() {
        ProcessoJudicialDTO processoJudicialDTO = new ProcessoJudicialDTO();
        when(processoJudicialRepository.save(any(ProcessoJudicial.class))).thenReturn(new ProcessoJudicial());
        ProcessoJudicial processoCriado = processoJudicialService.cadastrarProcesso(processoJudicialDTO);
        assertNotNull(processoCriado, "O processo judicial foi criado corretamente.");
    }

    @Test
    void naoDeveCriarProcessoComNumeroExistente() {
        ProcessoJudicialDTO processoJudicialDTO = new ProcessoJudicialDTO();
        processoJudicialDTO.setNumeroProcesso("12345");
        
        when(processoJudicialRepository.findByNumeroProcesso("12345")).thenReturn(true);
        try {
            processoJudicialService.cadastrarProcesso(processoJudicialDTO);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("já existe"), "Deve lançar exceção se o processo já existir.");
        }
    }

    @Test
    void deveRetornarListaCompletaDeProcessos() {
        when(processoJudicialRepository.findAll()).thenReturn(List.of(new ProcessoJudicial()));
        List<ProcessoJudicial> processos = processoJudicialService.listarProcessos(null, null);
        assertNotNull(processos, "A lista de processos não deve ser nula.");
        assertTrue(!processos.isEmpty(), "A lista de processos deve conter pelo menos um processo.");
    }

    @Test
    void deveRetornarProcessosPorStatusEComarca() {
        String status = "ATIVO";
        String comarca = "Comarca A";
        when(processoJudicialRepository.findByStatusAndComarca(StatusEnum.ATIVO, "Comarca A")).thenReturn(List.of(new ProcessoJudicial()));
        List<ProcessoJudicial> processos = processoJudicialService.listarProcessos(status, comarca);
        assertNotNull(processos, "A lista de processos não deve ser nula.");
        assertTrue(!processos.isEmpty(), "A lista de processos deve conter pelo menos um processo.");
    }

    @Test
    void deveRetornarProcessosPorStatus() {
        String status = "ATIVO";
        when(processoJudicialRepository.findByStatus(StatusEnum.ATIVO)).thenReturn(List.of(new ProcessoJudicial()));
        List<ProcessoJudicial> processos = processoJudicialService.listarProcessos(status, null);
        assertNotNull(processos, "A lista de processos não deve ser nula.");
        assertTrue(!processos.isEmpty(), "A lista de processos deve conter pelo menos um processo.");
    }

    @Test
    void naoDeveProcurarProcessosComStatusInvalido() {
        String status = "INVALIDO";
        try {
            processoJudicialService.listarProcessos(status, null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Status inválido"), "Deve lançar exceção se o status for inválido.");
        }
    }

    @Test
    void deveRetornarProcessosPorComarca() {
        String comarca = "Comarca A";
        when(processoJudicialRepository.findByComarca(comarca)).thenReturn(List.of(new ProcessoJudicial()));
        List<ProcessoJudicial> processos = processoJudicialService.listarProcessos(null, comarca);
        assertNotNull(processos, "A lista de processos não deve ser nula.");
        assertTrue(!processos.isEmpty(), "A lista de processos deve conter pelo menos um processo.");
    }
}
