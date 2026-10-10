package br.com.unisinos.trabalho.repository;

import br.com.unisinos.trabalho.model.Exercicio;
import br.com.unisinos.trabalho.model.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    List<Exercicio> findByGrupoMuscular(GrupoMuscular grupoMuscular);
}
