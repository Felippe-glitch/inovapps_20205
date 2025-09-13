package com.felippe.connection.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Faturamento;

@Repository
public interface FaturamentoRepository extends JpaRepository<Faturamento, Long> {

}
