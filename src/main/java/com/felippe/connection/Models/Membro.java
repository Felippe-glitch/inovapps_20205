package com.felippe.connection.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felippe.connection.Models.ENUMS.MembroStatus; 
import com.felippe.connection.Models.ENUMS.MembroType;  
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = Membro.TABLE_NAME)
@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
public class Membro {

    public static final String TABLE_NAME = "membros";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membro")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "negocios_fechados", nullable = false)
    private int negociosFechados = 0;

    @NotBlank
    @Size(min = 8, max = 100) 
    @Column(nullable = false, length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @URL(message = "A URL da foto é inválida.") 
    @Column(name = "foto_url")
    private String fotoUrl;

    @Past(message = "A data de nascimento deve ser no passado.")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "endereco", length = 200)
    private String local;

    @Column(name = "faturamento", precision = 19, scale = 2)
    private BigDecimal faturamento;

    @Column(name = "tempo_atuacao", length = 50)
    private String tempoAtuacao;

    @Lob
    @Column(name = "principais_resultados", columnDefinition = "TEXT")
    private String principaisResultados;

    @Column(name = "possui_filhos")
    private Boolean possuiFilhos;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String hobbies;

    @NotNull
    @Column(name = "contratos_entre_membros")
    private int contratosEntreMembros = 0;

    @NotNull
    @Column(name = "contratos_totais")
    private int contratosTotais = 0;

    @URL(message = "A URL do LinkedIn é inválida.")
    @Column(name = "link_linkedin")
    private String linkLinkedin;

    @URL(message = "A URL do site é inválida.")
    @Column(name = "link_site")
    private String linkSite;

    @Column(length = 50)
    private String instagram;

    @NotNull
    @Column(nullable = false)
    private Integer indicacao = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_membro", length = 20)
    private MembroType tipoMembro;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private MembroStatus status;
}