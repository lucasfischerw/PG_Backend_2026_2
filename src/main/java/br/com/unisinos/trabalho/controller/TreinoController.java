package br.com.unisinos.trabalho.controller;

import br.com.unisinos.trabalho.dto.TreinoDTO;
import br.com.unisinos.trabalho.dto.TreinoExercicioDTO;
import br.com.unisinos.trabalho.service.TreinoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Lista os treinos, opcionalmente filtrando por aluno")
    public List<TreinoDTO> listarTodos(@RequestParam(name = "aluno", required = false) Long alunoId) {
        return treinoService.listarTodos(alunoId);
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
    public ResponseEntity<TreinoDTO> criar(@Valid @RequestBody TreinoDTO dto) {
        TreinoDTO salvo = treinoService.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um treino")
    public ResponseEntity<TreinoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TreinoDTO dto) {
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

    @GetMapping("/{id}/exercicio")
    @Operation(summary = "Lista os exercícios de um treino")
    public ResponseEntity<List<TreinoExercicioDTO>> listarItens(@PathVariable Long id) {
        return treinoService.listarItens(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/exercicio")
    @Operation(summary = "Adiciona um exercício a um treino")
    public ResponseEntity<TreinoExercicioDTO> adicionarItem(@PathVariable Long id,
                                                            @Valid @RequestBody TreinoExercicioDTO dto) {
        return treinoService.adicionarItem(id, dto)
                .map(item -> ResponseEntity.status(HttpStatus.CREATED).body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/exercicio/{itemId}")
    @Operation(summary = "Remove um exercício de um treino")
    public ResponseEntity<Void> removerItem(@PathVariable Long id, @PathVariable Long itemId) {
        if (treinoService.removerItem(id, itemId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
