package com.felippe.connection.Repository;

import com.felippe.connection.Models.Faturamento;
import com.felippe.connection.Models.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importa a List padrão do Java

@Repository
public interface FaturamentoRepository extends JpaRepository<Faturamento, Long> {
    List<Faturamento> findByMembro(Membro membro);
}