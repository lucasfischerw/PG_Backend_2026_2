package br.com.unisinos.trabalho.controller;

import br.com.unisinos.trabalho.dto.InstrutorDTO;
import br.com.unisinos.trabalho.service.InstrutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/instrutor")
@Tag(name = "Instrutor", description = "Cadastro de instrutores")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os instrutores")
    public List<InstrutorDTO> listarTodos() {
        return instrutorService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um instrutor pelo id")
    public ResponseEntity<InstrutorDTO> buscarPorId(@PathVariable Long id) {
        return instrutorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra um instrutor")
    public ResponseEntity<InstrutorDTO> criar(@RequestBody InstrutorDTO dto) {
        InstrutorDTO salvo = instrutorService.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um instrutor")
    public ResponseEntity<InstrutorDTO> atualizar(@PathVariable Long id, @RequestBody InstrutorDTO dto) {
        return instrutorService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um instrutor")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (instrutorService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
