package com.felippe.connection.Models;

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
    name = "setores_has_membros",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_setor_membro",
            columnNames = {"setor_id", "membro_id"}
        )
    }
)
public class SetorHasMembro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "membro_id", nullable = false)
    private Membro membro;
}