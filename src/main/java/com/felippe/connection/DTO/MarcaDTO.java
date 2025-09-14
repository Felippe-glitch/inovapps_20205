package com.felippe.connection.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaDTO {

    @NotBlank(message = "O nome da marca não pode ser vazio.")
    @Size(min = 2, max = 100)
    private String nomeMarca;
}