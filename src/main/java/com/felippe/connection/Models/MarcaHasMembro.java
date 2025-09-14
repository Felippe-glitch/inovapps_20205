package com.felippe.connection.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(
    name = "marcas_has_membros",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_marca_membro",
            columnNames = {"marca_id", "membro_id"}
        )
    }
)
public class MarcaHasMembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference("membro-marcas")
    @JoinColumn(name = "membro_id", nullable = false)
    private Membro membro;

}