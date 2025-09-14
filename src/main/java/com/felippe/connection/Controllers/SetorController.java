package com.felippe.connection.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.felippe.connection.DTO.SetorDTO;
import com.felippe.connection.Models.Setor;
import com.felippe.connection.Service.SetorService;

@Controller
@RequestMapping("/setores")
public class SetorController {

    private SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    public ResponseEntity<Setor> criarSetor(@RequestBody SetorDTO setorDTO) {
        Setor novoSetor = new Setor();
        novoSetor.setNomeSetor(setorDTO.getNomeSetor());


        Setor setorSalvo = setorService.criarSetor(novoSetor);

        return ResponseEntity.status(HttpStatus.CREATED).body(setorSalvo);
    }
}
