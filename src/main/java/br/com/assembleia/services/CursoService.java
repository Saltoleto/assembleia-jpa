package br.com.assembleia.services;

import br.com.assembleia.entities.Curso;
import br.com.assembleia.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public void salvar(Curso curso) {
        cursoRepository.save(curso);
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public void deletar(Curso curso) {
        cursoRepository.delete(curso);
    }

    public List<Curso> listarPorIgreja(Long idIgreja) {
        return cursoRepository.listarPorIgreja(idIgreja);
    }
}
