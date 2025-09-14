package com.felippe.connection.DTO;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MembroDTO {

    // Dados Básicos
    private String nome;
    private String email;
    private String senha;

    // Dados do Perfil
    private String fotoUrl;
    private LocalDate dataNascimento;
    private String endereco;
    private String tempoAtuacao;
    private String principaisResultados;
    private Boolean possuiFilhos;
    private String hobbies;

    // Associações
    private List<String> nomesMarcas; // Nomes das marcas que ele representa
    private List<Long> idsEmpresas;   // IDs das empresas que ele selecionou
    private List<Long> idsSetores;    // IDs dos setores que ele selecionou
}