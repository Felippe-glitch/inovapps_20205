package com.felippe.connection.Service;

import com.felippe.connection.DTO.EmpresaAssociacaoDTO;
import com.felippe.connection.DTO.MembroDTO;
import com.felippe.connection.Models.*;
import com.felippe.connection.Models.ENUMS.MembroStatus;
import com.felippe.connection.Repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembroService {

    // --- DEPENDÊNCIAS ---
    private final MembroRepository membroRepository;
    private final MarcaRepository marcaRepository;
    private final MarcaHasMembroRepository marcaHasMembroRepository;
    private final EmpresaRepository empresaRepository;
    private final EmpresaHasMembroRepository empresaHasMembroRepository;
    private final SetorRepository setorRepository;
    private final SetorHasMembroRepository setorHasMembroRepository;
    private final PasswordEncoder passwordEncoder;

    // --- CONSTRUTOR COMPLETO ---
    public MembroService(MembroRepository membroRepository,
            MarcaRepository marcaRepository,
            MarcaHasMembroRepository marcaHasMembroRepository,
            EmpresaRepository empresaRepository,
            EmpresaHasMembroRepository empresaHasMembroRepository,
            SetorRepository setorRepository,
            SetorHasMembroRepository setorHasMembroRepository,
            PasswordEncoder passwordEncoder) {
        this.membroRepository = membroRepository;
        this.marcaRepository = marcaRepository;
        this.marcaHasMembroRepository = marcaHasMembroRepository;
        this.empresaRepository = empresaRepository;
        this.empresaHasMembroRepository = empresaHasMembroRepository;
        this.setorRepository = setorRepository;
        this.setorHasMembroRepository = setorHasMembroRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- CRUD BÁSICO ---

    /**
     * CREATE: Cria um novo membro com senha criptografada.
     */
    @Transactional
    public Membro criarMembro(Membro membro) {
        if (membroRepository.findByEmail(membro.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail informado já está em uso.");
        }
        membro.setSenha(passwordEncoder.encode(membro.getSenha()));
        return membroRepository.save(membro);
    }

    /**
     * READ ALL: Busca todos os membros de forma paginada.
     */
    @Transactional(readOnly = true)
    public Page<Membro> listarTodos(Pageable pageable) {
        return membroRepository.findAll(pageable);
    }

    /**
     * READ ONE: Busca um membro específico pelo seu ID.
     */
    @Transactional(readOnly = true)
    public Membro buscarPorId(Long id) {
        return membroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado com o ID: " + id));
    }

    /**
     * UPDATE: Atualiza os dados do perfil de um membro existente.
     */
    @Transactional
    public Membro atualizarPerfil(Long id, Membro membroDetails) {
        Membro membroExistente = buscarPorId(id);
        membroExistente.setNome(membroDetails.getNome());
        membroExistente.setFotoUrl(membroDetails.getFotoUrl());
        membroExistente.setEndereco(membroDetails.getEndereco());
        membroExistente.setHobbies(membroDetails.getHobbies());
        // ... outros campos que o usuário pode atualizar no perfil
        return membroRepository.save(membroExistente);
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

    // --- LÓGICA DE ASSOCIAÇÃO ---

    /**
     * Associa um membro a uma Marca. Se a Marca não existir, ela é criada.
     */
    @Transactional
    public void adicionarMarcaAoMembro(Long membroId, String nomeMarca) {
        Membro membro = buscarPorId(membroId);
        Marca marca = marcaRepository.findByNomeIgnoreCase(nomeMarca.trim())
                .orElseGet(() -> {
                    Marca novaMarca = new Marca();
                    novaMarca.setNome(nomeMarca.trim());
                    return marcaRepository.save(novaMarca);
                });

        if (marcaHasMembroRepository.existsByMembroAndMarca(membro, marca)) {
            throw new IllegalArgumentException("Este membro já está associado a esta marca.");
        }

        MarcaHasMembro novaAssociacao = new MarcaHasMembro();
        novaAssociacao.setMembro(membro);
        // ✅ CORREÇÃO: Adicione esta linha para ligar a associação à marca.
        novaAssociacao.setMarca(marca);

        membro.getMarcasAssociadas().add(novaAssociacao);
        membroRepository.save(membro);
    }

    /**
     * Associa um membro a uma Empresa (que deve ser pré-cadastrada).
     */
    // ...
    // ...
    @Transactional
    public void adicionarEmpresaAoMembro(Long membroId, EmpresaAssociacaoDTO dto) {
        Membro membro = buscarPorId(membroId);
        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + dto.getEmpresaId()));

        if (empresaHasMembroRepository.existsByMembroAndEmpresa(membro, empresa)) {
            throw new IllegalArgumentException("Este membro já está associado a esta empresa.");
        }

        EmpresaHasMembro novaAssociacao = new EmpresaHasMembro();
        novaAssociacao.setMembro(membro);
        novaAssociacao.setEmpresa(empresa);
        // ✅ LINHA "novaAssociacao.setCargo(...)" REMOVIDA

        membro.getEmpresasAssociadas().add(novaAssociacao);
        membroRepository.save(membro);
    }
    // ...
    // ...

    /**
     * Associa um membro a um Setor (que deve ser pré-cadastrado).
     * ESTE É O MÉTODO CORRETO PARA USAR.
     */
    @Transactional
    public void adicionarSetorAoMembro(Long membroId, Long setorId) {
        // 1. Busca o membro que receberá a associação.
        Membro membro = buscarPorId(membroId);

        // 2. BUSCA o setor que já existe no banco pelo ID.
        // Ele NÃO cria um novo setor.
        Setor setor = setorRepository.findById(setorId)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado com ID: " + setorId));

        // 3. Verifica se a associação já não foi feita.
        if (setorHasMembroRepository.existsByMembroAndSetor(membro, setor)) {
            throw new IllegalArgumentException("Este membro já está associado a este setor.");
        }

        // 4. CRIA APENAS A ASSOCIAÇÃO na tabela 'setores_has_membros'.
        SetorHasMembro novaAssociacao = new SetorHasMembro();
        novaAssociacao.setMembro(membro);
        novaAssociacao.setSetor(setor); // Usa o setor que foi encontrado.

        // 5. Adiciona a associação à lista do membro e salva.
        // O JPA/Hibernate cuidará de inserir a linha na tabela de junção.
        membro.getSetoresAssociados().add(novaAssociacao);
        membroRepository.save(membro);
    }

    @Transactional
    public Membro criarCadastroCompleto(MembroDTO dto) {
        // 1. Valida e cria o membro básico
        if (membroRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail informado já está em uso.");
        }

        Membro novoMembro = new Membro();
        novoMembro.setNome(dto.getNome());
        novoMembro.setEmail(dto.getEmail());
        novoMembro.setSenha(passwordEncoder.encode(dto.getSenha()));

        // 2. Preenche os dados do perfil
        novoMembro.setFotoUrl(dto.getFotoUrl());
        novoMembro.setDataNascimento(dto.getDataNascimento());
        novoMembro.setEndereco(dto.getEndereco());
        novoMembro.setTempoAtuacao(dto.getTempoAtuacao());
        novoMembro.setPrincipaisResultados(dto.getPrincipaisResultados());
        novoMembro.setPossuiFilhos(dto.getPossuiFilhos());
        novoMembro.setHobbies(dto.getHobbies());

        // Define valores padrão do sistema
        novoMembro.setStatus(MembroStatus.ATIVO);

        // 3. Salva o membro para obter um ID
        Membro membroSalvo = membroRepository.save(novoMembro);

        // 4. Processa e adiciona as associações
        // Marcas (com lógica de encontrar ou criar)
        if (dto.getNomesMarcas() != null) {
            for (String nomeMarca : dto.getNomesMarcas()) {
                adicionarMarcaAoMembro(membroSalvo.getId(), nomeMarca);
            }
        }

        // Empresas (apenas associa)
        if (dto.getIdsEmpresas() != null) {
            for (Long empresaId : dto.getIdsEmpresas()) {
                adicionarEmpresaAoMembro(membroSalvo.getId(), new EmpresaAssociacaoDTO(empresaId));
            }
        }

        // Setores (apenas associa)
        if (dto.getIdsSetores() != null) {
            for (Long setorId : dto.getIdsSetores()) {
                adicionarSetorAoMembro(membroSalvo.getId(), setorId);
            }
        }

        // 5. Retorna a versão final do membro com todas as associações
        // É importante buscar novamente para garantir que todos os dados estejam
        // carregados
        return buscarPorId(membroSalvo.getId());
    }

}