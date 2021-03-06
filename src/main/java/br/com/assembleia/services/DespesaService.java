package br.com.assembleia.services;


import br.com.assembleia.entities.Despesa;
import br.com.assembleia.entities.DespesasTipoDTO;
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

    public List<DespesasTipoDTO> listarDespesasTipoMesAnoCongregracao(Long idIgreja,Integer mes, Integer ano) {
        return despesaRepository.listarDespesasTipoMesAnoCongregracao(idIgreja,mes, ano);
    }

    public BigDecimal listarDespesasPagas() {
        return despesaRepository.listarDespesasPagas();
    }

    public BigDecimal buscarDespesaGrafico(Integer mes, Integer ano) {
        return despesaRepository.buscarDespesaGrafico(mes, ano);
    }

    public List<DespesasTipoDTO> listarDespesasTipoMesAno(Integer mes, Integer ano) {
        return despesaRepository.listarDespesasTipoMesAno(mes, ano);
    }

    public BigDecimal valorDespesasMesAnoCongregacao(Integer mes, Integer ano, Long id){
        return despesaRepository.valorDespesasMesAnoCongregacao(mes,ano,id);
    }

    public BigDecimal despesaParametroMeasAnoCongregacao(Integer mes, Integer ano, Long id, Boolean pago){
        return despesaRepository.despesaParametroMeasAnoCongregacao(mes,ano,id,pago);
    }

    public BigDecimal despesaParametroMeasAno(Integer mes, Integer ano,  Boolean pago){
        return despesaRepository.despesaParametroMeasAno(mes,ano,pago);
    }


    public List<Despesa> despesasMesAnoCongregacao(Integer mes, Integer ano, Long id){
        return despesaRepository.despesasMesAnoCongregacao(mes,ano,id);
    }

    public BigDecimal valorTotalDespesasCongregacao(Long id){
        return despesaRepository.valorTotalDespesasCongregacao(id);
    }

    public BigDecimal valorTotalDespesas(){
        return despesaRepository.valorTotalDespesas();
    }

    public BigDecimal listarDespesas(){
        return despesaRepository.listarDespesas();
    }

    public BigDecimal listarDespesasParametro(Boolean pago){
        return despesaRepository.listarDespesasParametro(pago);
    }
}
