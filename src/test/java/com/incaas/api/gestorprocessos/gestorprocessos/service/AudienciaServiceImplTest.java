package com.incaas.api.gestorprocessos.gestorprocessos.service;

import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.exception.BusinessException;
import com.incaas.api.gestorprocessos.model.Audiencia;
import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.EnumStatus;
import com.incaas.api.gestorprocessos.repository.AudienciaRepository;
import com.incaas.api.gestorprocessos.repository.ProcessoJudicialRepository;
import com.incaas.api.gestorprocessos.service.AudienciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

class AudienciaServiceImplTest {

    private AudienciaRepository audienciaRepository;
    private ProcessoJudicialRepository processoRepository;
    private ModelMapper modelMapper;
    private AudienciaServiceImpl audienciaService;

    @BeforeEach
    void setUp() {
        audienciaRepository = mock(AudienciaRepository.class);
        processoRepository = mock(ProcessoJudicialRepository.class);
        modelMapper = new ModelMapper();
        audienciaService = new AudienciaServiceImpl(audienciaRepository, modelMapper, processoRepository);
    }

    @Test
    void deveCadastrarAudienciaComSucesso() {
        Long idProcesso = 1L;
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setId(idProcesso);
        processo.setStatus(EnumStatus.ATIVO);
        processo.setVara("Vara1");

        AudienciaDTO dto = new AudienciaDTO();
        dto.setDataHora(LocalDateTime.parse("2024-06-10T10:00:00"));
        dto.setLocal("Sala 1");

        when(processoRepository.findById(idProcesso)).thenReturn(Optional.of(processo));
        when(audienciaRepository.findByProcessoAndLocal(dto.getDataHora(), processo.getVara(), dto.getLocal()))
                .thenReturn(Collections.emptyList());

        Audiencia audienciaSalva = new Audiencia();
        when(audienciaRepository.save(any(Audiencia.class))).thenReturn(audienciaSalva);

        Audiencia result = audienciaService.cadastrarAudiencia(idProcesso, dto);

        assertNotNull(result);
        verify(audienciaRepository).save(any(Audiencia.class));
    }

    @Test
    void deveLancarExcecaoQuandoProcessoNaoEncontrado() {
        Long idProcesso = 1L;
        AudienciaDTO dto = new AudienciaDTO();
        dto.setDataHora(LocalDateTime.parse("2024-06-10T10:00:00"));
        dto.setLocal("Sala 1");

        when(processoRepository.findById(idProcesso)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                audienciaService.cadastrarAudiencia(idProcesso, dto));
        assertEquals("Processo não encontrado.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoProcessoArquivadoOuSuspenso() {
        Long idProcesso = 1L;
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setId(idProcesso);
        processo.setStatus(EnumStatus.ARQUIVADO);
        processo.setVara("Vara1");

        AudienciaDTO dto = new AudienciaDTO();
        dto.setDataHora(LocalDateTime.parse("2024-06-10T10:00:00"));
        dto.setLocal("Sala 1");

        when(processoRepository.findById(idProcesso)).thenReturn(Optional.of(processo));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                audienciaService.cadastrarAudiencia(idProcesso, dto));
        assertEquals("Não é possível agendar audiência para processo arquivado ou suspenso.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoDataNaoForDiaUtil() {
        Long idProcesso = 1L;
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setId(idProcesso);
        processo.setStatus(EnumStatus.ATIVO);
        processo.setVara("Vara1");

        AudienciaDTO dto = new AudienciaDTO();
        dto.setDataHora(LocalDateTime.parse("2024-06-09T10:00:00"));
        dto.setLocal("Sala 1");

        when(processoRepository.findById(idProcesso)).thenReturn(Optional.of(processo));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                audienciaService.cadastrarAudiencia(idProcesso, dto));
        assertEquals("A data e hora da audiência devem ser em um dia útil.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoJaExisteAudienciaMesmaVaraELocal() {
        Long idProcesso = 1L;
        ProcessoJudicial processo = new ProcessoJudicial();
        processo.setId(idProcesso);
        processo.setStatus(EnumStatus.ATIVO);
        processo.setVara("Vara1");

        AudienciaDTO dto = new AudienciaDTO();
        dto.setDataHora(LocalDateTime.parse("2024-06-10T10:00:00"));
        dto.setLocal("Sala 1");

        when(processoRepository.findById(idProcesso)).thenReturn(Optional.of(processo));
        when(audienciaRepository.findByProcessoAndLocal(dto.getDataHora(), processo.getVara(), dto.getLocal()))
                .thenReturn(List.of(new Audiencia()));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                audienciaService.cadastrarAudiencia(idProcesso, dto));
        assertEquals("Já existe uma audiência agendada para a mesma data, horário, vara e local.", ex.getMessage());
    }

    @Test
    void deveBuscarTodasAudienciasQuandoParametrosNulosOuVazios() {
        when(audienciaRepository.findAll()).thenReturn(List.of(new Audiencia()));

        List<Audiencia> result = audienciaService.buscarAudienciasPorDataEComarca(null, null);
        assertEquals(1, result.size());
        verify(audienciaRepository).findAll();

        result = audienciaService.buscarAudienciasPorDataEComarca("", "");
        assertEquals(1, result.size());
        verify(audienciaRepository, times(2)).findAll();
    }

    @Test
    void deveBuscarAudienciasPorComarcaQuandoApenasComarcaInformada() {
        String comarca = "Comarca1";
        when(audienciaRepository.findByComarca(comarca)).thenReturn(List.of(new Audiencia()));

        List<Audiencia> result = audienciaService.buscarAudienciasPorDataEComarca(null, comarca);
        assertEquals(1, result.size());
        verify(audienciaRepository).findByComarca(comarca);

        result = audienciaService.buscarAudienciasPorDataEComarca("", comarca);
        assertEquals(1, result.size());
        verify(audienciaRepository, times(2)).findByComarca(comarca);
    }

    @Test
    void deveBuscarAudienciasPorDiaQuandoApenasDataInformada() {
        String data = "2024-06-10";
        when(audienciaRepository.findByDia(any(LocalDateTime.class), any(LocalDateTime.class), isNull()))
                .thenReturn(List.of(new Audiencia()));

        List<Audiencia> result = audienciaService.buscarAudienciasPorDataEComarca(data, null);
        assertEquals(1, result.size());
        verify(audienciaRepository).findByDia(any(LocalDateTime.class), any(LocalDateTime.class), isNull());
    }

    @Test
    void deveBuscarAudienciasPorComarcaEDiaQuandoAmbosInformados() {
        String data = "2024-06-10";
        String comarca = "Comarca1";
        when(audienciaRepository.findByComarcaAndDia(any(LocalDateTime.class), any(LocalDateTime.class), eq(comarca)))
                .thenReturn(List.of(new Audiencia()));

        List<Audiencia> result = audienciaService.buscarAudienciasPorDataEComarca(data, comarca);
        assertEquals(1, result.size());
        verify(audienciaRepository, atLeastOnce()).findByComarcaAndDia(any(LocalDateTime.class), any(LocalDateTime.class), eq(comarca));
    }


}