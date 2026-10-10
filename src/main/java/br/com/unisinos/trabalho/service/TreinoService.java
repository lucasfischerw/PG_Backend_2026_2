package br.com.unisinos.trabalho.service;

import br.com.unisinos.trabalho.dto.TreinoDTO;
import br.com.unisinos.trabalho.dto.TreinoExercicioDTO;
import br.com.unisinos.trabalho.model.Treino;
import br.com.unisinos.trabalho.model.TreinoExercicio;
import br.com.unisinos.trabalho.repository.AlunoRepository;
import br.com.unisinos.trabalho.repository.ExercicioRepository;
import br.com.unisinos.trabalho.repository.InstrutorRepository;
import br.com.unisinos.trabalho.repository.TreinoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final ExercicioRepository exercicioRepository;

    public TreinoService(TreinoRepository treinoRepository,
                         AlunoRepository alunoRepository,
                         InstrutorRepository instrutorRepository,
                         ExercicioRepository exercicioRepository) {
        this.treinoRepository = treinoRepository;
        this.alunoRepository = alunoRepository;
        this.instrutorRepository = instrutorRepository;
        this.exercicioRepository = exercicioRepository;
    }

    public List<TreinoDTO> listarTodos() {
        return treinoRepository.findAll().stream().map(TreinoDTO::deEntidade).toList();
    }

    public Optional<TreinoDTO> buscarPorId(Long id) {
        return treinoRepository.findById(id).map(TreinoDTO::deEntidade);
    }

    public TreinoDTO criar(TreinoDTO dto) {
        Treino treino = new Treino();
        preencher(treino, dto);
        return TreinoDTO.deEntidade(treinoRepository.saveAndFlush(treino));
    }

    public Optional<TreinoDTO> atualizar(Long id, TreinoDTO dto) {
        return treinoRepository.findById(id).map(treino -> {
            preencher(treino, dto);
            return TreinoDTO.deEntidade(treinoRepository.saveAndFlush(treino));
        });
    }

    public boolean excluir(Long id) {
        if (!treinoRepository.existsById(id)) {
            return false;
        }
        treinoRepository.deleteById(id);
        return true;
    }

    private void preencher(Treino treino, TreinoDTO dto) {
        treino.setNome(dto.nome());
        treino.setObjetivo(dto.objetivo());
        treino.setAluno(buscarReferencia(alunoRepository, dto.alunoId(), "Aluno"));
        treino.setInstrutor(buscarReferencia(instrutorRepository, dto.instrutorId(), "Instrutor"));

        // Composição: os itens pertencem ao treino e são substituídos a cada gravação
        treino.getItens().clear();
        if (dto.itens() != null) {
            for (TreinoExercicioDTO itemDto : dto.itens()) {
                TreinoExercicio item = new TreinoExercicio();
                item.setTreino(treino);
                item.setExercicio(buscarReferencia(exercicioRepository, itemDto.exercicioId(), "Exercicio"));
                item.setSeries(itemDto.series());
                item.setRepeticoes(itemDto.repeticoes());
                item.setCargaKg(itemDto.cargaKg());
                treino.getItens().add(item);
            }
        }
    }

    private <T> T buscarReferencia(JpaRepository<T, Long> repository, Long id, String entidade) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .orElseThrow(() -> new IllegalArgumentException(entidade + " não encontrado: " + id));
    }
}
