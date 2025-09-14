package com.felippe.connection.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetorDTO {

    @NotBlank(message = "O nome do setor não pode ser vazio.")
    @Size(min = 2, max = 100, message = "O nome do setor deve ter entre 2 e 100 caracteres.")
    private String nomeSetor;
}