package com.felippe.connection.Service;

import com.felippe.connection.Models.Empresa;
import com.felippe.connection.Repository.EmpresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public Empresa criarEmpresa(Empresa empresa) {
        if (empresaRepository.findByCnpj(empresa.getCnpj()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma empresa cadastrada com este CNPJ.");
        }
        return empresaRepository.save(empresa);
    }

    @Transactional(readOnly = true)
    public Page<Empresa> listarTodas(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    

    @Transactional(readOnly = true)
    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com o ID: " + id));
    }

    @Transactional
    public Empresa atualizarEmpresa(Long id, Empresa empresaDetails) {
        Empresa empresaExistente = buscarPorId(id);
        empresaExistente.setNomeEmpresa(empresaDetails.getNomeEmpresa());
        empresaExistente.setCnpj(empresaDetails.getCnpj());
        return empresaRepository.save(empresaExistente);
    }

    @Transactional
    public void deletarEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada com o ID: " + id);
        }
        empresaRepository.deleteById(id);
    }
}