package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.Instrutor;

public record InstrutorDTO(Long id, String nome, String cref) {

    public static InstrutorDTO deEntidade(Instrutor instrutor) {
        return new InstrutorDTO(instrutor.getId(), instrutor.getNome(), instrutor.getCref());
    }
}
