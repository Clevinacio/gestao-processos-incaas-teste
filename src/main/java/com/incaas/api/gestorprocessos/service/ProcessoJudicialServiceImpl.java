package com.incaas.api.gestorprocessos.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.gestorprocessos.model.enums.StatusEnum;
import com.incaas.api.gestorprocessos.dto.ProcessoJudicialDTO;
import com.incaas.api.gestorprocessos.exception.BusinessException;
import com.incaas.api.gestorprocessos.repository.ProcessoJudicialRepository;

@Service
public class ProcessoJudicialServiceImpl implements ProcessoJudicialService {

    private final ProcessoJudicialRepository processoJudicialRepository;
    private final ModelMapper modelMapper;

    public ProcessoJudicialServiceImpl(ProcessoJudicialRepository processoJudicialRepository, ModelMapper modelMapper) {
        this.processoJudicialRepository = processoJudicialRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProcessoJudicial cadastrarProcesso(ProcessoJudicialDTO processoJudicialDTO) {
        if(processoJaExiste(processoJudicialDTO.getNumeroProcesso())) {
            throw new BusinessException("Processo com número " + processoJudicialDTO.getNumeroProcesso() + " já existe.");
        }

        ProcessoJudicial processoJudicial = modelMapper.map(processoJudicialDTO, ProcessoJudicial.class);
        return processoJudicialRepository.save(processoJudicial);
    }

    private boolean processoJaExiste(String numeroProcesso) {
        return processoJudicialRepository.findByNumeroProcesso(numeroProcesso) != null;
    }
    
    @Override
    public List<ProcessoJudicial> listarProcessos(String status, String comarca) {
        StatusEnum statusEnum = null;
        if (status != null) {
            try {
                statusEnum = StatusEnum.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("Status inválido: " + status);
            }
        }
        if (statusEnum != null && comarca != null) {
            return processoJudicialRepository.findByStatusAndComarca(statusEnum, comarca);
        } else if (statusEnum != null) {
            return processoJudicialRepository.findByStatus(statusEnum);
        } else if (comarca != null) {
            return processoJudicialRepository.findByComarca(comarca);
        } else {
            return processoJudicialRepository.findAll();
        }
    }
}
