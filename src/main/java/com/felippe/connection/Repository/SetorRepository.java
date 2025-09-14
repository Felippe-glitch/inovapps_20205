package com.felippe.connection.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Membro;
import com.felippe.connection.Models.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    Optional<Setor> findByNomeSetor(String nomeSetor);
    
    @Query("SELECT shm.membro FROM SetorHasMembro shm WHERE shm.setor.id = :setorId")
    List<Membro> findAllBySetorId(@Param("setorId") Long setorId);
}
