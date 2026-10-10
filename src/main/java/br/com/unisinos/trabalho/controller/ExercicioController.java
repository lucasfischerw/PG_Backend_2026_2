package br.com.unisinos.trabalho.controller;

import br.com.unisinos.trabalho.dto.ExercicioDTO;
import br.com.unisinos.trabalho.model.GrupoMuscular;
import br.com.unisinos.trabalho.service.ExercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/exercicio")
@Tag(name = "Exercicio", description = "Cadastro de exercícios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    @GetMapping
    @Operation(summary = "Lista os exercícios, opcionalmente filtrando por grupo muscular")
    public List<ExercicioDTO> listarTodos(@RequestParam(required = false) GrupoMuscular grupoMuscular) {
        return exercicioService.listarTodos(grupoMuscular);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um exercício pelo id")
    public ResponseEntity<ExercicioDTO> buscarPorId(@PathVariable Long id) {
        return exercicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra um exercício")
    public ResponseEntity<ExercicioDTO> criar(@Valid @RequestBody ExercicioDTO dto) {
        ExercicioDTO salvo = exercicioService.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um exercício")
    public ResponseEntity<ExercicioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ExercicioDTO dto) {
        return exercicioService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um exercício")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (exercicioService.excluir(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
