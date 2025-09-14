package com.felippe.connection.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FaturamentoCreateDTO {

    @NotNull(message = "O valor não pode ser nulo.")
    @Positive(message = "O valor do faturamento deve ser positivo.")
    private BigDecimal valor;

    @NotNull(message = "A data não pode ser nula.")
    @PastOrPresent(message = "A data do faturamento не pode сер no futuro.")
    private LocalDate data;
}