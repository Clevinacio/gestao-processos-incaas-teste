package com.incaas.api.gestorprocessos.model;

import java.util.List;

import com.incaas.api.gestorprocessos.model.enums.EnumStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoJudicial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero", unique = true, nullable = false)
    private String numeroProcesso;

    @Column(nullable = false)
    private String vara;

    @Column(nullable = false)
    private String comarca;
    
    @Column(nullable = false)
    private String assunto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatus status;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Audiencia> audiencias;
}
