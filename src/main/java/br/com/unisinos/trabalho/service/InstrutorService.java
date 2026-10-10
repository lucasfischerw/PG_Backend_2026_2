package br.com.unisinos.trabalho.service;

import br.com.unisinos.trabalho.dto.InstrutorDTO;
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

    public List<InstrutorDTO> listarTodos() {
        return instrutorRepository.findAll().stream().map(InstrutorDTO::deEntidade).toList();
    }

    public Optional<InstrutorDTO> buscarPorId(Long id) {
        return instrutorRepository.findById(id).map(InstrutorDTO::deEntidade);
    }

    public InstrutorDTO criar(InstrutorDTO dto) {
        Instrutor instrutor = new Instrutor();
        preencher(instrutor, dto);
        return InstrutorDTO.deEntidade(instrutorRepository.save(instrutor));
    }

    public Optional<InstrutorDTO> atualizar(Long id, InstrutorDTO dto) {
        return instrutorRepository.findById(id).map(instrutor -> {
            preencher(instrutor, dto);
            return InstrutorDTO.deEntidade(instrutorRepository.save(instrutor));
        });
    }

    public boolean excluir(Long id) {
        if (!instrutorRepository.existsById(id)) {
            return false;
        }
        instrutorRepository.deleteById(id);
        return true;
    }

    private void preencher(Instrutor instrutor, InstrutorDTO dto) {
        instrutor.setNome(dto.nome());
        instrutor.setCref(dto.cref());
    }
}
