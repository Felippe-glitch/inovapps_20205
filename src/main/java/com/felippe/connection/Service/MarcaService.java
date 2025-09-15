package com.felippe.connection.Service;

import com.felippe.connection.DTO.MarcaDTO;
import com.felippe.connection.Models.Marca;
import com.felippe.connection.Repository.MarcaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Transactional
    public Marca criarMarca(MarcaDTO marcaDTO) {
        if (marcaRepository.findByNomeIgnoreCase(marcaDTO.getNomeMarca()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma marca cadastrada com este nome.");
        }
        Marca novaMarca = new Marca();
        novaMarca.setNome(marcaDTO.getNomeMarca());
        return marcaRepository.save(novaMarca);
    }

    @Transactional(readOnly = true)
    public Page<Marca> listarTodas(Pageable pageable) {
        return marcaRepository.findAll(pageable);
    }
    
}
