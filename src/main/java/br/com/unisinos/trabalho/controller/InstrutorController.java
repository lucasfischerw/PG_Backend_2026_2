package br.com.unisinos.trabalho.controller;

import br.com.unisinos.trabalho.model.Instrutor;
import br.com.unisinos.trabalho.service.InstrutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @GetMapping
    public List<Instrutor> listarTodos() {
        return instrutorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> buscarPorId(@PathVariable Long id) {
        return instrutorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instrutor> criar(@RequestBody Instrutor instrutor) {
        Instrutor salvo = instrutorService.salvar(instrutor);
        URI location = URI.create("/instrutores/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instrutor> atualizar(@PathVariable Long id, @RequestBody Instrutor instrutor) {
        return instrutorService.buscarPorId(id)
                .map(i -> {
                    instrutor.setId(id);
                    return ResponseEntity.ok(instrutorService.salvar(instrutor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (instrutorService.buscarPorId(id).isPresent()) {
            instrutorService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
