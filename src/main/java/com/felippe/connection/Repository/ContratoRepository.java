package com.felippe.connection.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Contrato;
import com.felippe.connection.Models.Membro;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    @Query("SELECT c FROM Contrato c WHERE c.membro1 = :membro OR c.membro2 = :membro ORDER BY c.dataContrato DESC")
    List<Contrato> findAllByParticipante(@Param("membro") Membro membro);

    List<Contrato> findByMembro1OrMembro2(Membro membro1, Membro membro2);
}
