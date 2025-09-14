package com.felippe.connection.Repository;

import com.felippe.connection.Models.Marca;
import com.felippe.connection.Models.MarcaHasMembro;
import com.felippe.connection.Models.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaHasMembroRepository extends JpaRepository<MarcaHasMembro, Long> {

    boolean existsByMembroAndMarca(Membro membro, Marca marca);
}