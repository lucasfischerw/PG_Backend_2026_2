package br.com.unisinos.trabalho.repository;

import br.com.unisinos.trabalho.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<Treino> findByAlunoId(Long alunoId);
    List<Treino> findByInstrutorId(Long instrutorId);
    List<Treino> findByDataTreino(LocalDate dataTreino);
}
