package com.felippe.connection.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contratante_id", nullable = false)
    private Membro membro_1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contratado_id", nullable = false)
    private Membro membro_2;

    @NotNull
    @PastOrPresent(message = "A data do contrato não pode ser no futuro.")
    @Column(name = "data_contrato", nullable = false)
    private LocalDate dataContrato;

    @NotNull
    @Positive(message = "O valor do contrato deve ser um número positivo.")
    @Column(name = "valor", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorContrato;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    @Column(name = "descricao", length = 500)
    private String descricao;
}