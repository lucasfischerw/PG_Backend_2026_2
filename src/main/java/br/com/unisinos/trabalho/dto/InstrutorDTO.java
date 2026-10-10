package br.com.unisinos.trabalho.dto;

import br.com.unisinos.trabalho.model.Instrutor;
import jakarta.validation.constraints.NotBlank;

public record InstrutorDTO(Long id, @NotBlank String nome, @NotBlank String cref) {

    public static InstrutorDTO deEntidade(Instrutor instrutor) {
        return new InstrutorDTO(instrutor.getId(), instrutor.getNome(), instrutor.getCref());
    }
}
