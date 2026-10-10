package br.com.unisinos.trabalho.service;

import br.com.unisinos.trabalho.model.Aluno;
import br.com.unisinos.trabalho.model.Exercicio;
import br.com.unisinos.trabalho.model.Instrutor;
import br.com.unisinos.trabalho.model.Treino;
import br.com.unisinos.trabalho.model.TreinoExercicio;
import br.com.unisinos.trabalho.repository.AlunoRepository;
import br.com.unisinos.trabalho.repository.ExercicioRepository;
import br.com.unisinos.trabalho.repository.InstrutorRepository;
import br.com.unisinos.trabalho.repository.TreinoExercicioRepository;
import br.com.unisinos.trabalho.repository.TreinoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final TreinoExercicioRepository treinoExercicioRepository;
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final ExercicioRepository exercicioRepository;

    public TreinoService(TreinoRepository treinoRepository,
                         TreinoExercicioRepository treinoExercicioRepository,
                         AlunoRepository alunoRepository,
                         InstrutorRepository instrutorRepository,
                         ExercicioRepository exercicioRepository) {
        this.treinoRepository = treinoRepository;
        this.treinoExercicioRepository = treinoExercicioRepository;
        this.alunoRepository = alunoRepository;
        this.instrutorRepository = instrutorRepository;
        this.exercicioRepository = exercicioRepository;
    }

    public List<Treino> listarTodos() {
        return treinoRepository.findAll();
    }

    public Optional<Treino> buscarPorId(Long id) {
        return treinoRepository.findById(id);
    }

    public List<Treino> buscarPorAluno(Long alunoId) {
        return treinoRepository.findByAlunoId(alunoId);
    }

    public List<Treino> buscarPorInstrutor(Long instrutorId) {
        return treinoRepository.findByInstrutorId(instrutorId);
    }

    public List<Treino> buscarPorData(LocalDate dataTreino) {
        return treinoRepository.findByDataTreino(dataTreino);
    }

    public Treino salvar(Treino treino) {
        return treinoRepository.save(treino);
    }

    public void excluir(Long id) {
        treinoRepository.deleteById(id);
    }

    public TreinoExercicio adicionarExercicio(Long treinoId, Long exercicioId, Integer series, Integer repeticoes, Double peso, Integer ordem) {
        Treino treino = treinoRepository.findById(treinoId)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));
        Exercicio exercicio = exercicioRepository.findById(exercicioId)
                .orElseThrow(() -> new RuntimeException("Exercicio não encontrado"));

        TreinoExercicio treinoExercicio = new TreinoExercicio();
        treinoExercicio.setTreino(treino);
        treinoExercicio.setExercicio(exercicio);
        treinoExercicio.setSeries(series);
        treinoExercicio.setRepeticoes(repeticoes);
        treinoExercicio.setPeso(peso);
        treinoExercicio.setOrdem(ordem);

        return treinoExercicioRepository.save(treinoExercicio);
    }

    public List<TreinoExercicio> listarExercicios(Long treinoId) {
        return treinoExercicioRepository.findByTreinoId(treinoId);
    }
}
