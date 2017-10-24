package br.com.assembleia.services;

import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.repositories.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    public void salvar(Congregacao congregacao) {
        sedeRepository.save(congregacao);
    }

    public List<Congregacao> listarTodos() {
        return sedeRepository.findAll();
    }

    public void deletar(Congregacao cargo) {
        sedeRepository.delete(cargo);
    }
    
    public List<Congregacao> listarSedes(){
        return sedeRepository.listarSedes();
    }
}
