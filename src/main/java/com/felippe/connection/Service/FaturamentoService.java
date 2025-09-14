package com.felippe.connection.Service;

import com.felippe.connection.DTO.FaturamentoCreateDTO;
import com.felippe.connection.Models.Faturamento;
import com.felippe.connection.Models.Membro;
import com.felippe.connection.Repository.FaturamentoRepository;
import com.felippe.connection.Repository.MembroRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaturamentoService {

    private final FaturamentoRepository faturamentoRepository;
    private final MembroRepository membroRepository;

    public FaturamentoService(FaturamentoRepository faturamentoRepository, MembroRepository membroRepository) {
        this.faturamentoRepository = faturamentoRepository;
        this.membroRepository = membroRepository;
    }

    @Transactional
    public Faturamento criarFaturamento(Long membroId, FaturamentoCreateDTO dto) {
        // 1. Busca o membro
        Membro membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado com ID: " + membroId));

        // 2. Cria a nova entidade Faturamento
        Faturamento novoFaturamento = new Faturamento();
        novoFaturamento.setMembro(membro);
        novoFaturamento.setValor(dto.getValor());
        novoFaturamento.setData(dto.getData());

        // 3. ATUALIZA O CONTADOR na entidade Membro
        membro.setFaturamento(membro.getFaturamento().add(dto.getValor()));
        membroRepository.save(membro); // Salva a atualização do membro

        // 4. Salva e retorna o novo registro de faturamento
        return faturamentoRepository.save(novoFaturamento);
    }

    /**
     * READ ONE: Busca um faturamento pelo seu ID.
     */
    @Transactional(readOnly = true)
    public Faturamento buscarPorId(Long faturamentoId) {
        return faturamentoRepository.findById(faturamentoId)
                .orElseThrow(() -> new RuntimeException("Faturamento não encontrado com ID: " + faturamentoId));
    }

    /**
     * READ ALL BY MEMBER: Lista todos os faturamentos de um membro específico.
     */
    @Transactional(readOnly = true)
    public List<Faturamento> listarFaturamentosPorMembro(Long membroId) {
        Membro membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado com ID: " + membroId));

        return faturamentoRepository.findByMembro(membro);
    }

    /**
     * DELETE: Deleta um registro de faturamento e subtrai o valor do total no
     * perfil do membro.
     * 
     * @param faturamentoId O ID do faturamento a ser deletado.
     */
    @Transactional
    public void deletarFaturamento(Long faturamentoId) {
        // 1. Busca o faturamento que será deletado
        Faturamento faturamento = buscarPorId(faturamentoId);

        // 2. Pega o membro associado
        Membro membro = faturamento.getMembro();

        // 3. SUBTRAI O VALOR do contador na entidade Membro
        membro.setFaturamento(membro.getFaturamento().subtract(faturamento.getValor()));
        membroRepository.save(membro); // Salva a atualização do membro

        // 4. Deleta o registro de faturamento
        faturamentoRepository.delete(faturamento);
    }
}