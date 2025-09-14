package com.felippe.connection.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmpresaAssociacaoDTO {

    @NotNull
    private Long empresaId;

}