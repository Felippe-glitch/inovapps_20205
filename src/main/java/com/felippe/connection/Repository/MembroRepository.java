package com.felippe.connection.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felippe.connection.Models.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {
    Optional<Membro> findByEmail(String email);

    @Query("SELECT DISTINCT m FROM Membro m JOIN Contrato c ON m = c.membro_1 OR m = c.membro_2 WHERE (c.membro_1 = :membro OR c.membro_2 = :membro) AND m != :membro")
    List<Membro> findDistinctParceirosDeContrato(@Param("membro") Membro membro);

    @Query("SELECT i.indicador FROM Indicacao i WHERE i.indicado = :membro ORDER BY i.dataIndicacao DESC")
    List<Membro> findIndicadoresByIndicado(@Param("membro") Membro membro);
}
