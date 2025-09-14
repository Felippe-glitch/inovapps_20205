package com.felippe.connection.Service;

import com.felippe.connection.DTO.MembroDTO;
import com.felippe.connection.DTO.MembroResponseDTO;
import com.felippe.connection.Models.*;
import com.felippe.connection.Models.ENUMS.MembroStatus;
import com.felippe.connection.Models.ENUMS.MembroType;
import com.felippe.connection.Repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MembroService {

    // --- DEPENDÊNCIAS (Injeção via construtor está correta) ---
    private final MembroRepository membroRepository;
    private final MarcaRepository marcaRepository;
    private final EmpresaRepository empresaRepository;
    private final SetorRepository setorRepository;
    private final IndicacaoRepository indicacaoRepository;
    private final MarcaHasMembroRepository marcaHasMembroRepository;
    private final EmpresaHasMembroRepository empresaHasMembroRepository;
    private final SetorHasMembroRepository setorHasMembroRepository;
    private final PasswordEncoder passwordEncoder;

    public MembroService(MembroRepository m, MarcaRepository ma, EmpresaRepository e, SetorRepository s,
            IndicacaoRepository i, MarcaHasMembroRepository mhm, EmpresaHasMembroRepository ehm,
            SetorHasMembroRepository shm, PasswordEncoder pe) {
        this.membroRepository = m;
        this.marcaRepository = ma;
        this.empresaRepository = e;
        this.setorRepository = s;
        this.indicacaoRepository = i;
        this.marcaHasMembroRepository = mhm;
        this.empresaHasMembroRepository = ehm;
        this.setorHasMembroRepository = shm;
        this.passwordEncoder = pe;
    }

    // --- OPERAÇÕES PÚBLICAS (A "API" DO SEU SERVICE) ---

    /**
     * CREATE: Cria um membro completo a partir de um DTO.
     */
    @Transactional
    public MembroResponseDTO criarMembro(MembroDTO dto) {
        if (membroRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail informado já está em uso.");
        }

        Membro novoMembro = new Membro();
        mapearCreateDtoParaEntidade(dto, novoMembro); // Usa o método auxiliar

        novoMembro.setSenha(passwordEncoder.encode(dto.getSenha()));
        novoMembro.setStatus(MembroStatus.ATIVO);
        novoMembro.setTipoMembro(MembroType.SOCIO);

        Membro membroSalvo = membroRepository.save(novoMembro);

        // Associações
        if (dto.getIdsMarcas() != null) {
            dto.getIdsMarcas().forEach(id -> adicionarMarcaAoMembro(membroSalvo, id));
        }
        if (dto.getIdsEmpresas() != null) {
            dto.getIdsEmpresas().forEach(id -> adicionarEmpresaAoMembro(membroSalvo, id));
        }
        if (dto.getIdsSetores() != null) {
            dto.getIdsSetores().forEach(id -> adicionarSetorAoMembro(membroSalvo, id));
        }

        return convertToResponseDTO(membroSalvo);
    }

    /**
     * READ ALL: Busca todos os membros de forma paginada e retorna como DTO.
     */
    @Transactional(readOnly = true)
    public List<MembroResponseDTO> listarTodos() {
        return membroRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * READ ONE: Busca um perfil completo de membro e retorna como DTO.
     */
    @Transactional(readOnly = true)
    public MembroResponseDTO buscarPerfilPorId(Long id) {
        Membro membro = buscarEntidadePorId(id);
        return convertToResponseDTO(membro);
    }

    /**
     * DELETE: Deleta um membro pelo seu ID.
     */
    @Transactional
    public void deletarMembro(Long id) {
        if (!membroRepository.existsById(id)) {
            throw new RuntimeException("Membro não encontrado com o ID: " + id);
        }
        membroRepository.deleteById(id);
    }

    // --- MÉTODOS AUXILIARES (PRIVADOS) ---

    private Membro buscarEntidadePorId(Long id) {
        return membroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado com o ID: " + id));
    }

    private void adicionarMarcaAoMembro(Membro membro, Long marcaId) {
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada com ID: " + marcaId));
        if (!marcaHasMembroRepository.existsByMembroAndMarca(membro, marca)) {
            MarcaHasMembro assoc = new MarcaHasMembro();
            assoc.setMembro(membro);
            assoc.setMarca(marca);
            membro.getMarcasAssociadas().add(assoc);
        }
    }

    private void adicionarEmpresaAoMembro(Membro membro, Long empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + empresaId));
        if (!empresaHasMembroRepository.existsByMembroAndEmpresa(membro, empresa)) {
            EmpresaHasMembro assoc = new EmpresaHasMembro();
            assoc.setMembro(membro);
            assoc.setEmpresa(empresa);
            membro.getEmpresasAssociadas().add(assoc);
        }
    }

    private void adicionarSetorAoMembro(Membro membro, Long setorId) {
        Setor setor = setorRepository.findById(setorId)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com ID: " + setorId));
        if (!setorHasMembroRepository.existsByMembroAndSetor(membro, setor)) {
            SetorHasMembro assoc = new SetorHasMembro();
            assoc.setMembro(membro);
            assoc.setSetor(setor);
            membro.getSetoresAssociados().add(assoc);
        }
    }

    private void mapearCreateDtoParaEntidade(MembroDTO dto, Membro membro) {
        membro.setNome(dto.getNome());
        membro.setEmail(dto.getEmail());
        membro.setFotoUrl(dto.getFotoUrl());
        membro.setDataNascimento(dto.getDataNascimento());
        membro.setPossuiFilhos(dto.getPossuiFilhos());
        membro.setHobbies(dto.getHobbies());
        membro.setLinkLinkedin(dto.getLinkLinkedin());
        membro.setLinkSite(dto.getLinkSite());
        membro.setInstagram(dto.getInstagram());
        membro.setBio(dto.getBio());
        if (dto.getFaturamento() != null) {
            membro.setFaturamento(dto.getFaturamento());
        }
        if (dto.getNegociosFechados() != null) {
            membro.setNegociosFechados(dto.getNegociosFechados());
        }
    }

    private MembroResponseDTO convertToResponseDTO(Membro membro) {
        MembroResponseDTO dto = new MembroResponseDTO();
        dto.setId(membro.getId());
        dto.setNome(membro.getNome());
        dto.setEmail(membro.getEmail());
        dto.setNegociosFechados(membro.getNegociosFechados());
        dto.setFaturamento(membro.getFaturamento());
        dto.setContratosTotais(membro.getContratosTotais());
        dto.setFotoUrl(membro.getFotoUrl());
        dto.setDataNascimento(membro.getDataNascimento());
        dto.setPossuiFilhos(membro.getPossuiFilhos());
        dto.setHobbies(membro.getHobbies());
        dto.setLinkLinkedin(membro.getLinkLinkedin());
        dto.setLinkSite(membro.getLinkSite());
        dto.setInstagram(membro.getInstagram());
        dto.setBio(membro.getBio());
        dto.setTipoMembro(membro.getTipoMembro());
        dto.setStatus(membro.getStatus());
        dto.setCriadoEm(membro.getCriadoEm());

        dto.setNomesMarcas(
                membro.getMarcasAssociadas().stream().map(m -> m.getMarca().getNome()).collect(Collectors.toList()));
        dto.setNomesEmpresas(membro.getEmpresasAssociadas().stream().map(e -> e.getEmpresa().getNomeEmpresa())
                .collect(Collectors.toList()));
        dto.setNomesSetores(membro.getSetoresAssociados().stream().map(s -> s.getSetor().getNomeSetor())
                .collect(Collectors.toList()));
        dto.setIndicacoesRecebidas(indicacaoRepository.countByIndicado(membro));

        return dto;
    }
}