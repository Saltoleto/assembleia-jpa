package br.com.assembleia.services;

import br.com.assembleia.entities.Fornecedor;
import br.com.assembleia.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public void salvar(Fornecedor fornecedor) {
        fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public void deletar(Fornecedor fornecedor) {
        fornecedorRepository.delete(fornecedor);
    }

    public List<Fornecedor> listarPorIgreja(Long idIgreja) {
        return  fornecedorRepository.listarPorIgreja(idIgreja);
    }
}
