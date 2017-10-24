package br.com.assembleia.services;

import br.com.assembleia.entities.TipoDeDespesa;
import br.com.assembleia.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void salvar(TipoDeDespesa cargo) {
        categoriaRepository.save(cargo);
    }

    public List<TipoDeDespesa> listarTodos() {
        return categoriaRepository.findAll();
    }

    public void deletar(TipoDeDespesa cargo) {
        categoriaRepository.delete(cargo);
    }

}
