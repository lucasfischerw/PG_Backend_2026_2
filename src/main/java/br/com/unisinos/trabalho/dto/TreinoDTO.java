package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.ObjetivoTreino;
import br.com.unisinos.trabalho.model.Treino;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TreinoDTO(
        Long id,
        @NotBlank String nome,
        @NotNull ObjetivoTreino objetivo,
        @NotNull Long alunoId,
        @NotNull Long instrutorId,
        @Valid List<TreinoExercicioDTO> itens
) {

    public static TreinoDTO deEntidade(Treino treino) {
        return new TreinoDTO(
                treino.getId(),
                treino.getNome(),
                treino.getObjetivo(),
                treino.getAluno().getId(),
                treino.getInstrutor().getId(),
                treino.getItens().stream().map(TreinoExercicioDTO::deEntidade).toList()
        );
    }
}
