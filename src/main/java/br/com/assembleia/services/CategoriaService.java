package br.com.assembleia.services;

import br.com.assembleia.entities.Categoria;
import br.com.assembleia.repositories.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void salvar(Categoria cargo) {
        categoriaRepository.save(cargo);
    }

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public void deletar(Categoria cargo) {
        categoriaRepository.delete(cargo);
    }

}
