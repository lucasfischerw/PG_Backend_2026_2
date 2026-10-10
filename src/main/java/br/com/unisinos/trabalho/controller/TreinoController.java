package br.com.unisinos.trabalho.controller;

import br.com.unisinos.trabalho.dto.TreinoDTO;
import br.com.unisinos.trabalho.service.TreinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/treino")
@Tag(name = "Treino", description = "Cadastro de treinos")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os treinos")
    public List<TreinoDTO> listarTodos() {
        return treinoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um treino pelo id")
    public ResponseEntity<TreinoDTO> buscarPorId(@PathVariable Long id) {
        return treinoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra um treino")
    public ResponseEntity<TreinoDTO> criar(@RequestBody TreinoDTO dto) {
        TreinoDTO salvo = treinoService.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um treino")
    public ResponseEntity<TreinoDTO> atualizar(@PathVariable Long id, @RequestBody TreinoDTO dto) {
        return treinoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um treino")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (treinoService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarReferenciaInvalida(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
