package com.felippe.connection.DTO;

import com.felippe.connection.Models.ENUMS.MembroStatus;
import com.felippe.connection.Models.ENUMS.MembroType;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para representar a visão completa do perfil de um Membro.
 * Usado como objeto de resposta da API para evitar LazyInitializationException
 * e para controlar os dados expostos.
 */
@Getter
@Setter
public class MembroPerfilDTO {

    // --- DADOS BÁSICOS E DE PERFIL ---
    private Long id;
    private String nome;
    private String email;
    private String fotoUrl;
    private LocalDate dataNascimento;
    private Boolean possuiFilhos;
    private String hobbies;
    private String linkLinkedin;
    private String linkSite;
    private String instagram;
    private String bio;
    
    // --- CONTADORES ---
    private int negociosFechados;
    private BigDecimal faturamento;
    private int contratosTotais;

    // --- DADOS DO SISTEMA ---
    private MembroType tipoMembro;
    private MembroStatus status;
    private LocalDateTime criadoEm;

    // --- DADOS DAS ASSOCIAÇÕES (já simplificados) ---
    private List<String> nomesMarcas;
    private List<String> nomesEmpresas;
    private List<String> nomesSetores;
}