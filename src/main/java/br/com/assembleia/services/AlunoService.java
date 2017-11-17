package br.com.assembleia.services;


import br.com.assembleia.entities.Aluno;
import br.com.assembleia.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public void salvar(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public void deletar(Aluno aluno) {
        alunoRepository.delete(aluno);
    }

}
