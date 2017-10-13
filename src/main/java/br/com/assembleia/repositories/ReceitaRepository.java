
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Receita;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReceitaRepository extends JpaRepository<Receita, Integer>
{
    BigDecimal valorReceitaPeriodo(Integer mes, Integer ano);
    BigDecimal listarReceitasRecebidas();
    List<Receita> listarReceitasMesAno(Integer mes, Integer ano);
    BigDecimal buscarReceitaGrafico(Long mes, Integer ano);
    BigDecimal listarReceitasCategoriaMesAno(Long id,Integer mes, Integer ano);
    List<Receita> listarUltimasReceitasVisao(Integer mes, Integer ano);
    List<Receita> buscarReceitaMembroData(Long mes);
}

