package br.com.unisinos.trabalho.repository;

import br.com.unisinos.trabalho.model.TreinoExercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Long> {
    List<TreinoExercicio> findByTreinoId(Long treinoId);
}
