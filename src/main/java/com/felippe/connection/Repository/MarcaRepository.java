package com.felippe.connection.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

}
