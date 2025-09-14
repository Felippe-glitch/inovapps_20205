package com.felippe.connection.Repository;

import com.felippe.connection.Models.Indicacao;
import com.felippe.connection.Models.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicacaoRepository extends JpaRepository<Indicacao, Long> {
    long countByIndicado(Membro indicado);
    boolean existsByIndicadorAndIndicado(Membro indicador, Membro indicado);
    List<Indicacao> findTop5ByOrderByDataIndicacaoDesc();
}