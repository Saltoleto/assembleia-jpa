package br.com.assembleia.services;

import br.com.assembleia.entities.Receita;
import br.com.assembleia.entities.ReceitasTipoDTO;
import br.com.assembleia.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public void salvar(Receita cargo) {
        receitaRepository.save(cargo);
    }

    public List<Receita> listarTodos() {
        return receitaRepository.findAll();
    }

    public void deletar(Receita cargo) {
        receitaRepository.delete(cargo);
    }

    public BigDecimal valorReceitaPeriodo(Integer mes, Integer ano) {
        return receitaRepository.valorReceitaPeriodo(mes, ano);
    }

    public BigDecimal buscarReceitaGrafico(Integer mes, Integer ano){
       return receitaRepository.buscarReceitaGrafico(mes,ano);
    }

    public BigDecimal listarReceitasParametro(Boolean recebido) {
        return receitaRepository.listarReceitasParametro(recebido);
    }

    public BigDecimal listarReceitasTipoMesAno(Long idTipoReceita,Integer mes, Integer ano) {
        return receitaRepository.listarReceitasTipoMesAno(idTipoReceita,mes, ano);
    }

    public List<Receita> listarReceitasMesAno(Integer mes, Integer ano) {
        return receitaRepository.listarReceitasMesAno(mes, ano);
    }

    public List<Receita> listarReceitasMembroAnaliticoMesAno(Integer mes, Integer ano, Boolean recebido) {
        return receitaRepository.listarReceitasMembroAnaliticoMesAno(mes, ano, recebido);
    }

    public List<Receita> listarReceitasMembroAnaliticoMesAnoIgreja(Integer mes, Integer ano, Long idIgreja, Boolean recebido) {
        return receitaRepository.listarReceitasMembroAnaliticoMesAnoIgreja(mes, ano,idIgreja, recebido);
    }

    public BigDecimal receitasMembroParametroMeasAno(Integer mes, Integer ano, Boolean recebido) {
        return receitaRepository.receitasMembroParametroMeasAno(mes, ano,recebido);
    }

    public BigDecimal receitasMembroParametroMeasAnoIgreja(Integer mes, Integer ano,Long idIgreja, Boolean recebido) {
        return receitaRepository.receitasMembroParametroMeasAnoIgreja(mes, ano, idIgreja,recebido);
    }

    public List<Receita> buscarReceitaMembroData(Long mes) {
        return receitaRepository.buscarReceitaMembroData(mes);
    }

    public List<Receita> listarPorIgreja(Long idIgreja) {
        return receitaRepository.listarPorIgreja(idIgreja);
    }

    public List<Receita> listarReceitasMesAnoCongregacao(Integer mes, Integer ano, Long id) {
        return receitaRepository.listarReceitasMesAnoCongregacao(mes, ano, id);
    }

    public BigDecimal receitasRecebidasMeasAnoCongregacao(Integer mes, Integer ano, Long id) {
        return receitaRepository.receitasRecebidasMeasAnoCongregacao(mes, ano, id);
    }

    public BigDecimal receitasParametroMeasAnoCongregacao(Integer mes, Integer ano, Long id, Boolean recebido) {
        return receitaRepository.receitasParametroMeasAnoCongregacao(mes, ano, id, recebido);
    }

    public BigDecimal valorTotalReceitas(Long id) {
        return receitaRepository.valorTotalReceitas(id);
    }

    public BigDecimal receitasParametroMeasAno(Integer mes, Integer ano, Boolean recebido) {
        return receitaRepository.receitasParametroMeasAno(mes, ano, recebido);
    }

    public BigDecimal listarReceitas(){
        return receitaRepository.listarReceitas();
    }

    public List<ReceitasTipoDTO> listarReceitaTipoMesAno(Integer mes,Integer ano){
        return receitaRepository.listarReceitaTipoMesAno(mes,ano);
    }

    public List<ReceitasTipoDTO> listarReceitaTipoMesAnoCongregracao(Long idIgreja,Integer mes,Integer ano){
        return receitaRepository.listarReceitaTipoMesAnoCongregracao(idIgreja,mes,ano);
    }

    public List<ReceitasTipoDTO> listarReceitaMesAno(Integer mes,Integer ano){
        return receitaRepository.listarReceitaMesAno(mes,ano);
    }
    public List<ReceitasTipoDTO> listarReceitaMesAnoCongregacao(Long idIgreja,Integer mes,Integer ano){
        return receitaRepository.listarReceitaMesAnoCongregacao(idIgreja,mes,ano);
    }

    public List<Receita> listarMembroTipoIgrejaMesAno(String tipo,Long idIgreja, Integer mes, Integer ano){
        return receitaRepository.listarMembroTipoIgrejaMesAno(tipo,idIgreja,mes,ano);
    }


    public List<Receita> listarMembroTipoMesAno(String tipo,Integer mes, Integer ano){
        return receitaRepository.listarMembroTipoMesAno(tipo,mes,ano);
    }
}
