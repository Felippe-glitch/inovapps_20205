package com.felippe.connection.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MembroDTO {

     @NotBlank @Size(min = 3, max = 255) private String nome;
    @NotBlank @Email private String email;
    @NotBlank @Size(min = 8) private String senha;
    @URL private String fotoUrl;
    @Past private LocalDate dataNascimento;
    private Boolean possuiFilhos;
    private String hobbies;
    @URL private String linkLinkedin;
    @URL private String linkSite;
    private String instagram;
    @Size(max = 1000) private String bio;
    private List<Long> idsMarcas;
    private List<Long> idsEmpresas;
    private List<Long> idsSetores;
    @PositiveOrZero(message = "O faturamento não pode ser negativo.")
    private BigDecimal faturamento;
    @PositiveOrZero(message = "O número de negócios fechados не pode ser negativo.")
    private Integer negociosFechados;
}