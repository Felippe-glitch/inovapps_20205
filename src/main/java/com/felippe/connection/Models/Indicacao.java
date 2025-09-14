package com.felippe.connection.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "indicacoes")
public class Indicacao {

    @Id
    @GeneratedValue
    private Long id;

    // Quem FEZ a indicação (o "fã")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicador_id")
    private Membro indicador;

    // Quem RECEBEU a indicação (o "ídolo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicado_id")
    @JsonBackReference
    private Membro indicado;

    @CreationTimestamp
    private LocalDateTime dataIndicacao;
}
