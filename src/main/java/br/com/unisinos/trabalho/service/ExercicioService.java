package br.com.unisinos.trabalho.service;

import br.com.unisinos.trabalho.model.Exercicio;
import br.com.unisinos.trabalho.model.GrupoMuscular;
import br.com.unisinos.trabalho.repository.ExercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;

    public ExercicioService(ExercicioRepository exercicioRepository) {
        this.exercicioRepository = exercicioRepository;
    }

    public List<Exercicio> listarTodos() {
        return exercicioRepository.findAll();
    }

    public Optional<Exercicio> buscarPorId(Long id) {
        return exercicioRepository.findById(id);
    }

    public List<Exercicio> buscarPorGrupoMuscular(GrupoMuscular grupoMuscular) {
        return exercicioRepository.findByGrupoMuscular(grupoMuscular);
    }

    public Exercicio salvar(Exercicio exercicio) {
        return exercicioRepository.save(exercicio);
    }

    public void excluir(Long id) {
        exercicioRepository.deleteById(id);
    }
}
