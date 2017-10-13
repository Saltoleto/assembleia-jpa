package br.com.assembleia.services;

import br.com.assembleia.entities.Receita;
import br.com.assembleia.repositories.ReceitaRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public BigDecimal listarReceitasRecebidas() {
        return receitaRepository.listarReceitasRecebidas();
    }

    public List<Receita> listarReceitasMesAno(Integer mes, Integer ano) {
        return receitaRepository.listarReceitasMesAno(mes, ano);
    }
    
    public BigDecimal buscarReceitaGrafico(Long mes, Integer ano){
        return receitaRepository.buscarReceitaGrafico(mes,ano);
    }
    
    public BigDecimal listarReceitasCategoriaMesAno(Long id,Integer mes, Integer ano){
        return receitaRepository.listarReceitasCategoriaMesAno(id, mes, ano);
    }
    
    public List<Receita> listarUltimasReceitasVisao(Integer mes, Integer ano){
        return receitaRepository.listarUltimasReceitasVisao(mes, ano);
    }
    
    public List<Receita> buscarReceitaMembroData(Long mes) {
        return receitaRepository.buscarReceitaMembroData(mes);
    }

}
