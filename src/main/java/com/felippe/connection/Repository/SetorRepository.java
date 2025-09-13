package com.felippe.connection.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

}
