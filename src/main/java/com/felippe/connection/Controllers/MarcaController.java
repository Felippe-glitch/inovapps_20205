package com.felippe.connection.Controllers;

import com.felippe.connection.DTO.MarcaDTO;
import com.felippe.connection.Models.Marca;
import com.felippe.connection.Service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    /**
     * POST: Endpoint para criar uma nova marca (uso administrativo).
     */
    @PostMapping
    public ResponseEntity<Marca> criarMarca(@Valid @RequestBody MarcaDTO marcaDTO) {
        Marca marcaSalva = marcaService.criarMarca(marcaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaSalva);
    }

    /**
     * GET: Endpoint para listar todas as marcas de forma paginada.
     */
    @GetMapping
    public ResponseEntity<Page<Marca>> listarMarcas(Pageable pageable) {
        return ResponseEntity.ok(marcaService.listarTodas(pageable));
    }

    // ... outros endpoints (GET por ID, PUT, DELETE)
}