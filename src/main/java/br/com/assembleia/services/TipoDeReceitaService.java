package br.com.assembleia.services;


import br.com.assembleia.entities.TipoDeReceita;
import br.com.assembleia.repositories.TipoDeReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoDeReceitaService {

    @Autowired
    private TipoDeReceitaRepository tipoDeReceitaRepository;

    public void salvar(TipoDeReceita tipoDeReceita) {
        tipoDeReceitaRepository.save(tipoDeReceita);
    }

    public List<TipoDeReceita> listarTodos() {
        return tipoDeReceitaRepository.findAll();
    }

    public void deletar(TipoDeReceita tipoDeReceita) {
        tipoDeReceitaRepository.delete(tipoDeReceita);
    }
}
