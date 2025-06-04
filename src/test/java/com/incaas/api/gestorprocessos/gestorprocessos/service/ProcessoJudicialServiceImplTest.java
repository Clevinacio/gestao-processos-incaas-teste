package com.incaas.api.gestorprocessos.gestorprocessos.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.incaas.api.gestorprocessos.repository.ProcessoJudicialRepository;
import com.incaas.api.gestorprocessos.service.ProcessoJudicialServiceImpl;

class ProcessoJudicialServiceImplTest {
    @InjectMocks
    private ProcessoJudicialServiceImpl processoJudicialService;

    @Mock
    private ProcessoJudicialRepository processoJudicialRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarProcesso() {
        assertTrue(true, "Teste de cadastro de processo judicial deve ser implementado");
    }
}
