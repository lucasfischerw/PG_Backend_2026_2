package br.com.unisinos.trabalho.controller;

import br.com.unisinos.trabalho.model.Exercicio;
import br.com.unisinos.trabalho.model.GrupoMuscular;
import br.com.unisinos.trabalho.service.ExercicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    @GetMapping
    public List<Exercicio> listarTodos() {
        return exercicioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercicio> buscarPorId(@PathVariable Long id) {
        return exercicioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "grupoMuscular")
    public List<Exercicio> buscarPorGrupoMuscular(@RequestParam GrupoMuscular grupoMuscular) {
        return exercicioService.buscarPorGrupoMuscular(grupoMuscular);
    }

    @PostMapping
    public ResponseEntity<Exercicio> criar(@RequestBody Exercicio exercicio) {
        Exercicio salvo = exercicioService.salvar(exercicio);
        URI location = URI.create("/exercicios/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercicio> atualizar(@PathVariable Long id, @RequestBody Exercicio exercicio) {
        return exercicioService.buscarPorId(id)
                .map(e -> {
                    exercicio.setId(id);
                    return ResponseEntity.ok(exercicioService.salvar(exercicio));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (exercicioService.buscarPorId(id).isPresent()) {
            exercicioService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
