package br.com.unisinos.trabalho.service;

import br.com.unisinos.trabalho.model.Instrutor;
import br.com.unisinos.trabalho.repository.InstrutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;

    public InstrutorService(InstrutorRepository instrutorRepository) {
        this.instrutorRepository = instrutorRepository;
    }

    public List<Instrutor> listarTodos() {
        return instrutorRepository.findAll();
    }

    public Optional<Instrutor> buscarPorId(Long id) {
        return instrutorRepository.findById(id);
    }

    public Optional<Instrutor> buscarPorEmail(String email) {
        return instrutorRepository.findByEmail(email);
    }

    public Instrutor salvar(Instrutor instrutor) {
        return instrutorRepository.save(instrutor);
    }

    public void excluir(Long id) {
        instrutorRepository.deleteById(id);
    }
}
