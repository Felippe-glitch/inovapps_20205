package com.felippe.connection.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

}
