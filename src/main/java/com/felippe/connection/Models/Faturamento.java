package com.felippe.connection.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entidade que representa o Faturamento de um Membro.
 *
 * Corrige os tipos inadequados da modelagem original (VARCHAR para valor e data)
 * para tipos específicos e seguros, garantindo a integridade dos dados.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "faturamento") 
public class Faturamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFaturamento")
    private Long id;

    @NotNull(message = "O valor não pode ser nulo.")
    @Positive(message = "O valor do faturamento deve ser positivo.")
    @Column(name = "valor", nullable = false, precision = 19, scale = 2) 
    private BigDecimal valor;

    @NotNull(message = "A data não pode ser nula.")
    @PastOrPresent(message = "A data do faturamento não pode ser no futuro.")
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_membro", nullable = false) 
    @JsonBackReference
    private Membro membro;

}