package com.felippe.connection.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Empresa.TABLE_NAME)
public class Empresa {
    public static final String TABLE_NAME = "empresas";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long id;

    @Column(name = "nome_empresa", unique = true, nullable = false)
    @NotBlank
    private String nomeEmpresa;

    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    @NotBlank
    private String cnpj;

}
