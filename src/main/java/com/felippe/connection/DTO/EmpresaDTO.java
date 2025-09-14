package com.felippe.connection.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
public class EmpresaDTO {

    @NotBlank(message = "O nome da empresa não pode ser vazio.")
    @Size(min = 2, max = 100)
    private String nomeEmpresa;

    @NotBlank(message = "O CNPJ não pode ser vazio.")
    @CNPJ(message = "O CNPJ informado é inválido.")
    private String cnpj;
}