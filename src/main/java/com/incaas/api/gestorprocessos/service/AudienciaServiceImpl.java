package com.incaas.api.gestorprocessos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.incaas.api.gestorprocessos.dto.AudienciaDTO;
import com.incaas.api.gestorprocessos.exception.BusinessException;
import com.incaas.api.gestorprocessos.model.Audiencia;
import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.EnumStatus;
import com.incaas.api.gestorprocessos.repository.AudienciaRepository;
import com.incaas.api.gestorprocessos.repository.ProcessoJudicialRepository;

@Service
public class AudienciaServiceImpl implements AudienciaService {
    AudienciaRepository audienciaRepository;
    ProcessoJudicialRepository processoRepository;
    ModelMapper modelMapper = new ModelMapper();

    public AudienciaServiceImpl(AudienciaRepository audienciaRepository, ModelMapper modelMapper, ProcessoJudicialRepository processoRepository) {
        this.processoRepository = processoRepository;
        this.modelMapper = modelMapper;
        this.audienciaRepository = audienciaRepository;
    }
    
    @Override
    public Audiencia cadastrarAudiencia(Long idProcesso, AudienciaDTO audienciaDTO) {
        ProcessoJudicial processo = validarAudiencia(audienciaDTO, idProcesso);
        Audiencia audiencia = modelMapper.map(audienciaDTO, Audiencia.class);
        audiencia.setProcesso(processo);
        return audienciaRepository.save(audiencia);
    }

    private ProcessoJudicial validarAudiencia(AudienciaDTO audienciaDTO, Long idProcesso) {
        ProcessoJudicial processo = processoRepository.findById(idProcesso)
                .orElseThrow(() -> new BusinessException("Processo não encontrado."));
        if(processoArquivadoSuspenso(processo)) {
            throw new BusinessException("Não é possível agendar audiência para processo arquivado ou suspenso.");
        }
        if(!diaUtil(audienciaDTO.getDataHora())) {
            throw new BusinessException("A data e hora da audiência devem ser em um dia útil.");
        }
        if(audienciaMesmaVaraELocal(processo.getVara(), audienciaDTO.getLocal(), audienciaDTO.getDataHora())) {
            throw new BusinessException("Já existe uma audiência agendada para a mesma data, horário, vara e local.");
        }

        return processo;
    }

    private boolean audienciaMesmaVaraELocal(String vara, String local, LocalDateTime dataHora) {
        List<Audiencia> audienciaExistente = audienciaRepository.findByProcessoAndLocal(
            dataHora, 
            vara, 
            local
        );
        return !audienciaExistente.isEmpty();
    }

    private boolean diaUtil(LocalDateTime dataHora) {
        int diaDaSemana = dataHora.getDayOfWeek().getValue();
        return diaDaSemana >= 1 && diaDaSemana <= 5;
    }

    private boolean processoArquivadoSuspenso(ProcessoJudicial processo) {
        return processo.getStatus().equals(EnumStatus.ARQUIVADO) || processo.getStatus().equals(EnumStatus.SUSPENSO);
    }

}
