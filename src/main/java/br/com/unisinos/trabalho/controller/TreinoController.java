package br.com.unisinos.trabalho.controller;

import br.com.unisinos.trabalho.model.Treino;
import br.com.unisinos.trabalho.model.TreinoExercicio;
import br.com.unisinos.trabalho.service.TreinoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/treinos")
public class TreinoController {

    private final TreinoService treinoService;

    public TreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @GetMapping
    public List<Treino> listarTodos() {
        return treinoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treino> buscarPorId(@PathVariable Long id) {
        return treinoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "alunoId")
    public List<Treino> buscarPorAluno(@RequestParam Long alunoId) {
        return treinoService.buscarPorAluno(alunoId);
    }

    @GetMapping(params = "instrutorId")
    public List<Treino> buscarPorInstrutor(@RequestParam Long instrutorId) {
        return treinoService.buscarPorInstrutor(instrutorId);
    }

    @GetMapping(params = "dataTreino")
    public List<Treino> buscarPorData(@RequestParam LocalDate dataTreino) {
        return treinoService.buscarPorData(dataTreino);
    }

    @PostMapping
    public ResponseEntity<Treino> criar(@RequestBody Treino treino) {
        Treino salvo = treinoService.salvar(treino);
        URI location = URI.create("/treinos/" + salvo.getId());
        return ResponseEntity.created(location).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> atualizar(@PathVariable Long id, @RequestBody Treino treino) {
        return treinoService.buscarPorId(id)
                .map(t -> {
                    treino.setId(id);
                    return ResponseEntity.ok(treinoService.salvar(treino));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (treinoService.buscarPorId(id).isPresent()) {
            treinoService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{treinoId}/exercicios")
    public ResponseEntity<TreinoExercicio> adicionarExercicio(
            @PathVariable Long treinoId,
            @RequestBody TreinoExercicio treinoExercicio) {
        try {
            TreinoExercicio salvo = treinoService.adicionarExercicio(
                    treinoId,
                    treinoExercicio.getExercicio().getId(),
                    treinoExercicio.getSeries(),
                    treinoExercicio.getRepeticoes(),
                    treinoExercicio.getPeso(),
                    treinoExercicio.getOrdem()
            );
            return ResponseEntity.ok(salvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{treinoId}/exercicios")
    public List<TreinoExercicio> listarExercicios(@PathVariable Long treinoId) {
        return treinoService.listarExercicios(treinoId);
    }
}
