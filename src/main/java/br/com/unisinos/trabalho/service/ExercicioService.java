package br.com.unisinos.trabalho.service;

import br.com.unisinos.trabalho.dto.ExercicioDTO;
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

    public List<ExercicioDTO> listarTodos(GrupoMuscular grupoMuscular) {
        List<Exercicio> exercicios;
        if (grupoMuscular != null) {
            exercicios = exercicioRepository.findByGrupoMuscular(grupoMuscular);
        } else {
            exercicios = exercicioRepository.findAll();
        }
        return exercicios.stream().map(ExercicioDTO::deEntidade).toList();
    }

    public Optional<ExercicioDTO> buscarPorId(Long id) {
        return exercicioRepository.findById(id).map(ExercicioDTO::deEntidade);
    }

    public ExercicioDTO criar(ExercicioDTO dto) {
        Exercicio exercicio = new Exercicio();
        preencher(exercicio, dto);
        return ExercicioDTO.deEntidade(exercicioRepository.save(exercicio));
    }

    public Optional<ExercicioDTO> atualizar(Long id, ExercicioDTO dto) {
        return exercicioRepository.findById(id).map(exercicio -> {
            preencher(exercicio, dto);
            return ExercicioDTO.deEntidade(exercicioRepository.save(exercicio));
        });
    }

    public boolean excluir(Long id) {
        if (!exercicioRepository.existsById(id)) {
            return false;
        }
        exercicioRepository.deleteById(id);
        return true;
    }

    private void preencher(Exercicio exercicio, ExercicioDTO dto) {
        exercicio.setNome(dto.nome());
        exercicio.setGrupoMuscular(dto.grupoMuscular());
    }
}
