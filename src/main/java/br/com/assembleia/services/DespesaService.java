package br.com.assembleia.services;


import br.com.assembleia.entities.Despesa;
import br.com.assembleia.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public void salvar(Despesa despesa) {
        despesaRepository.save(despesa);
    }

    public List<Despesa> listarTodos() {
        return despesaRepository.findAll();
    }

    public void deletar(Despesa despesa) {
        despesaRepository.delete(despesa);
    }

    public Despesa getById(final Long id) {
        return despesaRepository.getById(id);
    }

    public BigDecimal valorDespesaPeriodo(Integer mes, Integer ano) {
        return despesaRepository.valorDespesaPeriodo(mes, ano);
    }

    public List<Despesa> listarDespesasMesAno(Integer mes, Integer ano) {
        return despesaRepository.listarDespesasMesAno(mes, ano);
    }

    public List<Despesa> despesasPagarVisaoGeral(Integer mes, Integer ano) {
        return despesaRepository.despesasPagarVisaoGeral(mes, ano);
    }

    public BigDecimal listarDespesasPagas() {
        return despesaRepository.listarDespesasPagas();
    }

    public BigDecimal buscarDespesaGrafico(Integer mes, Integer ano) {
        return despesaRepository.buscarDespesaGrafico(mes, ano);
    }

    public BigDecimal listarDespesasTipoMesAno(Integer mes, Integer ano, Long id) {
        return despesaRepository.listarDespesasTipoMesAno(mes, ano, id);
    }
}
