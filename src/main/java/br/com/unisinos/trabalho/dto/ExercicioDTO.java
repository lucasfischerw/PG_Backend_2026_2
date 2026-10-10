package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.Exercicio;
import br.com.unisinos.trabalho.model.GrupoMuscular;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExercicioDTO(Long id, @NotBlank String nome, @NotNull GrupoMuscular grupoMuscular) {

    public static ExercicioDTO deEntidade(Exercicio exercicio) {
        return new ExercicioDTO(exercicio.getId(), exercicio.getNome(), exercicio.getGrupoMuscular());
    }
}
