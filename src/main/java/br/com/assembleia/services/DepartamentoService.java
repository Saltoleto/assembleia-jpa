package br.com.assembleia.services;

import br.com.assembleia.entities.Departamento;
import br.com.assembleia.repositories.DepartamentoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public void salvar(Departamento cargo) {
        departamentoRepository.save(cargo);
    }

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public void deletar(Departamento cargo) {
        departamentoRepository.delete(cargo);
    }
}
