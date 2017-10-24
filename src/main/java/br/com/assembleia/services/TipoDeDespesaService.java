package br.com.assembleia.services;

import br.com.assembleia.entities.TipoDeDespesa;
import br.com.assembleia.repositories.TipoDeDespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipoDeDespesaService {

    @Autowired
    private TipoDeDespesaRepository tipoDeDespesaRepository;

    public void salvar(TipoDeDespesa tipoDeDespesa) {
        tipoDeDespesaRepository.save(tipoDeDespesa);
    }

    public List<TipoDeDespesa> listarTodos() {
        return tipoDeDespesaRepository.findAll();
    }

    public void deletar(TipoDeDespesa tipoDeDespesa) {
        tipoDeDespesaRepository.delete(tipoDeDespesa);
    }
}
