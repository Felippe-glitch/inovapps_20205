package com.felippe.connection.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContratoDTO {

    @NotNull(message = "O ID do primeiro membro é obrigatório.")
    private Long membro1Id;

    @NotNull(message = "O ID do segundo membro é obrigatório.")
    private Long membro2Id;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    private String descricao;

    @NotNull(message = "A data do contrato é obrigatória.")
    @PastOrPresent(message = "A data do contrato não pode ser no futuro.")
    private LocalDate dataContrato;

    @NotNull(message = "O valor do contrato é obrigatório.")
    @Positive(message = "O valor do contrato deve ser um número positivo.")
    private BigDecimal valorContrato;
}