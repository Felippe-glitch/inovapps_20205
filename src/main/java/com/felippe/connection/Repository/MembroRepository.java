package com.felippe.connection.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {

}
