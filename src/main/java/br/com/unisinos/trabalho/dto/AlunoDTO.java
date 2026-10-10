package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.Aluno;

public record AlunoDTO(Long id, String nome, String email) {

    public static AlunoDTO deEntidade(Aluno aluno) {
        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }
}
