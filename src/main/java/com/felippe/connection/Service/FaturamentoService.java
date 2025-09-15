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
        Membro membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado com ID: " + membroId));

        Faturamento novoFaturamento = new Faturamento();
        novoFaturamento.setMembro(membro);
        novoFaturamento.setValor(dto.getValor());
        novoFaturamento.setData(dto.getData());

        membro.setFaturamento(membro.getFaturamento().add(dto.getValor()));
        membroRepository.save(membro); // Salva a atualização do membro

        return faturamentoRepository.save(novoFaturamento);
    }

    @Transactional(readOnly = true)
    public Faturamento buscarPorId(Long faturamentoId) {
        return faturamentoRepository.findById(faturamentoId)
                .orElseThrow(() -> new RuntimeException("Faturamento não encontrado com ID: " + faturamentoId));
    }

    @Transactional(readOnly = true)
    public List<Faturamento> listarFaturamentosPorMembro(Long membroId) {
        Membro membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado com ID: " + membroId));

        return faturamentoRepository.findByMembro(membro);
    }

    @Transactional
    public void deletarFaturamento(Long faturamentoId) {
        Faturamento faturamento = buscarPorId(faturamentoId);

        Membro membro = faturamento.getMembro();

        membro.setFaturamento(membro.getFaturamento().subtract(faturamento.getValor()));
        membroRepository.save(membro);

        faturamentoRepository.delete(faturamento);
    }
}
