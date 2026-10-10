package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.TreinoExercicio;

public record TreinoExercicioDTO(Long id, Long exercicioId, Integer series, Integer repeticoes, Double cargaKg) {

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
