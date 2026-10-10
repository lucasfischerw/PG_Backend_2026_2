package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.ObjetivoTreino;
import br.com.unisinos.trabalho.model.Treino;

import java.util.List;

public record TreinoDTO(
        Long id,
        String nome,
        ObjetivoTreino objetivo,
        Long alunoId,
        Long instrutorId,
        List<TreinoExercicioDTO> itens
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
