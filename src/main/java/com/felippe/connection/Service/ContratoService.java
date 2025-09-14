package com.felippe.connection.Service;

import com.felippe.connection.DTO.ContratoDTO; // Usaremos um DTO para entrada de dados
import com.felippe.connection.Models.Contrato;
import com.felippe.connection.Models.Membro;
import com.felippe.connection.Repository.ContratoRepository;
import com.felippe.connection.Repository.MembroRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final MembroRepository membroRepository;

    public ContratoService(ContratoRepository contratoRepository, MembroRepository membroRepository) {
        this.contratoRepository = contratoRepository;
        this.membroRepository = membroRepository;
    }

    @Transactional
    public Contrato criarContrato(ContratoDTO dto) {
        Membro membro1 = membroRepository.findById(dto.getMembro1Id())
                .orElseThrow(() -> new RuntimeException("Membro 1 não encontrado com ID: " + dto.getMembro1Id()));
        
        Membro membro2 = membroRepository.findById(dto.getMembro2Id())
                .orElseThrow(() -> new RuntimeException("Membro 2 não encontrado com ID: " + dto.getMembro2Id()));

        if (membro1.equals(membro2)) {
            throw new IllegalArgumentException("Um membro não pode criar um contrato consigo mesmo.");
        }

        // 3. Cria a entidade Contrato
        Contrato novoContrato = new Contrato();
        novoContrato.setMembro1(membro1);
        novoContrato.setMembro2(membro2);
        novoContrato.setDescricao(dto.getDescricao());
        novoContrato.setDataContrato(dto.getDataContrato());
        novoContrato.setValorContrato(dto.getValorContrato());

        Contrato contratoSalvo = contratoRepository.save(novoContrato);

        // 4. ATUALIZA OS CONTADORES dos membros
        membro1.setContratosTotais(membro1.getContratosTotais() + 1);
        membro2.setContratosTotais(membro2.getContratosTotais() + 1);
        // Supondo que "negociosFechados" também deva ser incrementado
        membro1.setNegociosFechados(membro1.getNegociosFechados() + 1);
        membro2.setNegociosFechados(membro2.getNegociosFechados() + 1);

        membroRepository.save(membro1);
        membroRepository.save(membro2);
        
        return contratoSalvo;
    }
    
    @Transactional(readOnly = true)
    public Contrato buscarPorId(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Contrato> listarTodos(Pageable pageable) {
        return contratoRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public List<Contrato> listarPorMembro(Long membroId){
        Membro membro = membroRepository.findById(membroId)
            .orElseThrow(() -> new RuntimeException("Membro não encontrado com ID: " + membroId));
            
        return contratoRepository.findByMembro1OrMembro2(membro, membro);
    }

    @Transactional
    public Contrato atualizarContrato(Long id, ContratoDTO dto) {
        Contrato contratoExistente = buscarPorId(id);
        
        contratoExistente.setDescricao(dto.getDescricao());
        contratoExistente.setDataContrato(dto.getDataContrato());
        contratoExistente.setValorContrato(dto.getValorContrato());

        return contratoRepository.save(contratoExistente);
    }

    @Transactional
    public void deletarContrato(Long id) {
        Contrato contrato = buscarPorId(id);
        
        Membro membro1 = contrato.getMembro1();
        Membro membro2 = contrato.getMembro2();

        membro1.setContratosTotais(membro1.getContratosTotais() - 1);
        membro2.setContratosTotais(membro2.getContratosTotais() - 1);
        membro1.setNegociosFechados(membro1.getNegociosFechados() - 1);
        membro2.setNegociosFechados(membro2.getNegociosFechados() - 1);

        membroRepository.save(membro1);
        membroRepository.save(membro2);

        contratoRepository.delete(contrato);
    }
}