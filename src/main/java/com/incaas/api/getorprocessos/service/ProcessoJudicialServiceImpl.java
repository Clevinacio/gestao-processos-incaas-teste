package com.incaas.api.getorprocessos.service;

import org.modelmapper.ModelMapper;

import com.incaas.api.gestorprocessos.model.ProcessoJudicial;
import com.incaas.api.getorprocessos.dto.ProcessoJudicialDTO;
import com.incaas.api.getorprocessos.repository.ProcessoJudicialRepository;

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
            throw new IllegalArgumentException("Processo com número " + processoJudicialDTO.getNumeroProcesso() + " já existe.");
        }

        ProcessoJudicial processoJudicial = modelMapper.map(processoJudicialDTO, ProcessoJudicial.class);
        return processoJudicialRepository.save(processoJudicial);
    }

    private boolean processoJaExiste(String numeroProcesso) {
        return processoJudicialRepository.findByNumeroProcesso(numeroProcesso) != null;
    }

    @Override
    public ProcessoJudicial buscarProcessoPorId(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'buscarProcessoPorId'");
    }

    @Override
    public ProcessoJudicial atualizarProcesso(Long id, ProcessoJudicialDTO processoJudicialDTO) {
        throw new UnsupportedOperationException("Unimplemented method 'atualizarProcesso'");
    }

}
