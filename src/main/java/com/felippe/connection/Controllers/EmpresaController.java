package com.felippe.connection.Controllers;

import com.felippe.connection.DTO.EmpresaDTO;
import com.felippe.connection.Models.Empresa;
import com.felippe.connection.Service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        Empresa novaEmpresa = new Empresa();
        novaEmpresa.setNomeEmpresa(empresaDTO.getNomeEmpresa());
        novaEmpresa.setCnpj(empresaDTO.getCnpj());
        
        Empresa empresaSalva = empresaService.criarEmpresa(novaEmpresa);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
    }

    @GetMapping
    public ResponseEntity<Page<Empresa>> listarTodas(Pageable pageable) {
        Page<Empresa> empresas = empresaService.listarTodas(pageable);
        return ResponseEntity.ok(empresas);
    }

    /**
     * READ ONE: Endpoint para buscar uma empresa pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        Empresa empresa = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaDTO empresaDTO) {
        Empresa empresaParaAtualizar = new Empresa();
        empresaParaAtualizar.setNomeEmpresa(empresaDTO.getNomeEmpresa());
        empresaParaAtualizar.setCnpj(empresaDTO.getCnpj());

        Empresa empresaAtualizada = empresaService.atualizarEmpresa(id, empresaParaAtualizar);
        return ResponseEntity.ok(empresaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 No Content
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}