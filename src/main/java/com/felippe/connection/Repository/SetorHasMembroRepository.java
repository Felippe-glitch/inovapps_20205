package com.felippe.connection.Repository;

import com.felippe.connection.Models.Membro;
import com.felippe.connection.Models.Setor;
import com.felippe.connection.Models.SetorHasMembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorHasMembroRepository extends JpaRepository<SetorHasMembro, Long> {

    boolean existsByMembroAndSetor(Membro membro, Setor setor);
}