package com.felippe.connection.Repository;

import com.felippe.connection.Models.Marca;
import com.felippe.connection.Models.MarcaHasMembro;
import com.felippe.connection.Models.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaHasMembroRepository extends JpaRepository<MarcaHasMembro, Long> {

    /**
     * Verifica de forma eficiente se uma associação específica entre um membro e uma marca já existe.
     * O Spring Data JPA cria a query automaticamente a partir do nome do método.
     * É essencial para evitar associações duplicadas no seu service.
     *
     * @param membro O objeto Membro.
     * @param marca O objeto Marca.
     * @return true se a associação já existir, false caso contrário.
     */
    boolean existsByMembroAndMarca(Membro membro, Marca marca);
}