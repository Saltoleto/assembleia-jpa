package br.com.assembleia.services;

import br.com.assembleia.entities.Cargo;
import br.com.assembleia.repositories.CargoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public void salvar(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }   

    public void deletar(Cargo cargo){
        cargoRepository.delete(cargo);
    }

}
