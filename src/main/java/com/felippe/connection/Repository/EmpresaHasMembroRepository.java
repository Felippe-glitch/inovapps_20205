package com.felippe.connection.Repository;

import com.felippe.connection.Models.Empresa;
import com.felippe.connection.Models.EmpresaHasMembro;
import com.felippe.connection.Models.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaHasMembroRepository extends JpaRepository<EmpresaHasMembro, Long> {
    boolean existsByMembroAndEmpresa(Membro membro, Empresa empresa);
}