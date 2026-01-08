package com.example.PsiSoftware.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataSessao;

    @ManyToOne
    private Paciente paciente;
    private BigDecimal valor;
    private StatusSessao status;
}
