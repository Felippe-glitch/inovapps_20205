package com.felippe.connection.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felippe.connection.Models.ENUMS.MembroStatus;
import com.felippe.connection.Models.ENUMS.MembroType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade central da aplicação, representando um Membro/Usuário.
 * Atua como uma Raiz de Agregação (Aggregate Root), conectando-se
 * a todas as outras entidades relacionadas.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "membros")
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membro")
    private Long id;

    // --- DADOS PRINCIPAIS E CREDENCIAIS ---
    @NotBlank
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String nome;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 8)
    @Column(nullable = false, length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Impede que a senha seja enviada em respostas JSON
    private String senha;

    // --- CONTADORES MANUAIS (Gerenciados pelo Service) ---
    @Builder.Default
    @Column(name = "negocios_fechados", nullable = false)
    private int negociosFechados = 0;

    @Builder.Default
    @Column(name = "faturamento", nullable = false, precision = 19, scale = 2)
    private BigDecimal faturamento = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "contratos_totais", nullable = false)
    private int contratosTotais = 0;

    // --- DADOS DO PERFIL ---
    @URL(message = "A URL da foto é inválida.")
    @Column(name = "foto_url")
    private String fotoUrl;

    @Past(message = "A data de nascimento deve ser no passado.")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "endereco", length = 200)
    private String endereco;

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

    @URL(message = "A URL do LinkedIn é inválida.")
    @Column(name = "link_linkedin")
    private String linkLinkedin;

    @URL(message = "A URL do site é inválida.")
    @Column(name = "link_site")
    private String linkSite;

    @Column(length = 50)
    private String instagram;
    
    // --- DADOS DE CONTROLE DO SISTEMA ---
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_membro", length = 20)
    private MembroType tipoMembro;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private MembroStatus status;

    // --- CAMPOS DE AUDITORIA ---
    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    // --- RELACIONAMENTOS ---
    @Builder.Default
    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("membro-marcas")
    private Set<MarcaHasMembro> marcasAssociadas = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("membro-empresas")
    private Set<EmpresaHasMembro> empresasAssociadas = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("membro-setores")
    private Set<SetorHasMembro> setoresAssociados = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "membro1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("membro1-contratos")
    private Set<Contrato> contratosComoMembro1 = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "membro2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("membro2-contratos")
    private Set<Contrato> contratosComoMembro2 = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("membro-faturamentos")
    private Set<Faturamento> faturamentos = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "indicador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("indicador-indicacoes")
    private Set<Indicacao> indicacoesFeitas = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "indicado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("indicado-indicacoes")
    private Set<Indicacao> indicacoesRecebidas = new HashSet<>();
}