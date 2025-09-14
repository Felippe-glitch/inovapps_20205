package com.felippe.connection.DTO;

import com.felippe.connection.Models.ENUMS.MembroStatus;
import com.felippe.connection.Models.ENUMS.MembroType;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MembroResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private int negociosFechados;
    private BigDecimal faturamento;
    private int contratosTotais;
    private String fotoUrl;
    private LocalDate dataNascimento;
    private Boolean possuiFilhos;
    private String hobbies;
    private String linkLinkedin;
    private String linkSite;
    private String instagram;
    private String bio;
    private MembroType tipoMembro;
    private MembroStatus status;
    private LocalDateTime criadoEm;
    private List<String> nomesMarcas;
    private List<String> nomesEmpresas;
    private List<String> nomesSetores;
    private Long indicacoesRecebidas;
}