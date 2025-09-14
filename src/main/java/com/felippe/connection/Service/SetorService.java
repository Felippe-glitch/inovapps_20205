package com.felippe.connection.Service;

import com.felippe.connection.DTO.SetorDTO; // Importa o DTO
import com.felippe.connection.Models.Setor;
import com.felippe.connection.Repository.SetorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    @Transactional
    public Setor criarSetor(Setor novoSetor2) {
        if (setorRepository.findByNomeSetorIgnoreCase(novoSetor2.getNomeSetor()).isPresent()) {
            throw new IllegalArgumentException("Já existe um setor cadastrado com este nome.");
        }

        Setor novoSetor = new Setor();
        novoSetor.setNomeSetor(novoSetor2.getNomeSetor());

        return setorRepository.save(novoSetor);
    }

    /**
     * READ ALL: Busca todos os setores de forma paginada.
     */
    @Transactional(readOnly = true)
    public Page<Setor> listarTodos(Pageable pageable) {
        return setorRepository.findAll(pageable);
    }

    /**
     * READ ONE: Busca um setor específico pelo seu ID.
     */
    @Transactional(readOnly = true)
    public Setor buscarPorId(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com o ID: " + id));
    }

    /**
     * UPDATE: Atualiza os dados de um setor existente.
     */
    @Transactional
    public Setor atualizarSetor(Long id, SetorDTO setorDTO) {
        Setor setorExistente = buscarPorId(id);
        
        // ✅ CORREÇÃO: Linha duplicada e lógica do CNPJ removidas.
        setorExistente.setNomeSetor(setorDTO.getNomeSetor());
        
        return setorRepository.save(setorExistente);
    }

    @Transactional
    public void deletarSetor(Long id) {
        if (!setorRepository.existsById(id)) {
            throw new RuntimeException("Setor não encontrado com o ID: " + id);
        }
        setorRepository.deleteById(id);
    }
}