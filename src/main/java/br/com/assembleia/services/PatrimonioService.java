package br.com.assembleia.services;

import br.com.assembleia.entities.Patrimonio;
import br.com.assembleia.repositories.PatrimonioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PatrimonioService {

    @Autowired
    private PatrimonioRepository patrimonioRepository;

    public void salvar(Patrimonio cargo) {
        patrimonioRepository.save(cargo);
    }

    public List<Patrimonio> listarTodos() {
        return patrimonioRepository.findAll();
    }

    public void deletar(Patrimonio cargo) {
        patrimonioRepository.delete(cargo);
    }
    
    public BigDecimal valorPatrimonio(){
        return patrimonioRepository.valorPatrimonio();
    }

    public List<Patrimonio> listarPorIgreja(Long idIgreja) {
        return patrimonioRepository.listarPorIgreja(idIgreja);
    }
}
