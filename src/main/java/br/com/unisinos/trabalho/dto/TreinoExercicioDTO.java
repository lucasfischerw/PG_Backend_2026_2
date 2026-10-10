package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.TreinoExercicio;
import jakarta.validation.constraints.NotNull;

public record TreinoExercicioDTO(
        Long id,
        @NotNull Long exercicioId,
        @NotNull Integer series,
        @NotNull Integer repeticoes,
        Double cargaKg
) {

    public static TreinoExercicioDTO deEntidade(TreinoExercicio item) {
        return new TreinoExercicioDTO(
                item.getId(),
                item.getExercicio().getId(),
                item.getSeries(),
                item.getRepeticoes(),
                item.getCargaKg()
        );
    }
}
