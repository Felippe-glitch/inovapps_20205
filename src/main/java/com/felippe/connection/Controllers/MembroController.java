package com.felippe.connection.Controllers;

import com.felippe.connection.DTO.MembroDTO;
import com.felippe.connection.Models.Membro;
import com.felippe.connection.Service.MembroService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Membro> criarMembroCompleto(@Valid @RequestBody MembroDTO membroDTO) {
        Membro membroSalvo = membroService.criarCadastroCompleto(membroDTO);
        

        return ResponseEntity.status(HttpStatus.CREATED).body(membroSalvo);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}