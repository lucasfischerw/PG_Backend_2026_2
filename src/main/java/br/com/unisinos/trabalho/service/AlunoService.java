package br.com.unisinos.trabalho.service;

import br.com.unisinos.trabalho.dto.AlunoDTO;
import br.com.unisinos.trabalho.model.Aluno;
import br.com.unisinos.trabalho.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<AlunoDTO> listarTodos() {
        return alunoRepository.findAll().stream().map(AlunoDTO::deEntidade).toList();
    }

    public Optional<AlunoDTO> buscarPorId(Long id) {
        return alunoRepository.findById(id).map(AlunoDTO::deEntidade);
    }

    public AlunoDTO criar(AlunoDTO dto) {
        Aluno aluno = new Aluno();
        preencher(aluno, dto);
        return AlunoDTO.deEntidade(alunoRepository.save(aluno));
    }

    public Optional<AlunoDTO> atualizar(Long id, AlunoDTO dto) {
        return alunoRepository.findById(id).map(aluno -> {
            preencher(aluno, dto);
            return AlunoDTO.deEntidade(alunoRepository.save(aluno));
        });
    }

    public boolean excluir(Long id) {
        if (!alunoRepository.existsById(id)) {
            return false;
        }
        alunoRepository.deleteById(id);
        return true;
    }

    private void preencher(Aluno aluno, AlunoDTO dto) {
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
    }
}
