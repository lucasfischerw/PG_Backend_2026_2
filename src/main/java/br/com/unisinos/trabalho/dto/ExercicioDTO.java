package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.Exercicio;
import br.com.unisinos.trabalho.model.GrupoMuscular;

public record ExercicioDTO(Long id, String nome, GrupoMuscular grupoMuscular) {

    public static ExercicioDTO deEntidade(Exercicio exercicio) {
        return new ExercicioDTO(exercicio.getId(), exercicio.getNome(), exercicio.getGrupoMuscular());
    }
}
