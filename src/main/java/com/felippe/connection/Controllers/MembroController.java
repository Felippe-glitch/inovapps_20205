package com.felippe.connection.Controllers;

import com.felippe.connection.DTO.MembroDTO;
import com.felippe.connection.DTO.MembroResponseDTO;
import com.felippe.connection.Service.MembroService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membros")
public class MembroController {

    private final MembroService membroService;

    public MembroController(MembroService membroService) {
        this.membroService = membroService;
    }

    @PostMapping
    public ResponseEntity<MembroResponseDTO> criarMembro(@Valid @RequestBody MembroDTO membroCreateDTO) {
        MembroResponseDTO membroSalvoDTO = membroService.criarMembro(membroCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(membroSalvoDTO);
    }

    @GetMapping
    public ResponseEntity<List<MembroResponseDTO>> listarTodos() {
        List<MembroResponseDTO> membrosDTO = membroService.listarTodos();
        return ResponseEntity.ok(membrosDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMembro(@PathVariable Long id) {
        membroService.deletarMembro(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}