package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.Aluno;
import jakarta.validation.constraints.NotBlank;

public record AlunoDTO(Long id, @NotBlank String nome, @NotBlank String email) {

    public static AlunoDTO deEntidade(Aluno aluno) {
        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }
}
