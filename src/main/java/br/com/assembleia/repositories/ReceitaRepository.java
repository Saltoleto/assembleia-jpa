
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
    BigDecimal valorReceitaPeriodo(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal listarReceitasRecebidas();

    List<Receita> listarReceitasMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal buscarReceitaGrafico(@Param("mes") Long mes,@Param("ano") Integer ano);

    BigDecimal listarReceitasCategoriaMesAno(@Param("id") Long id, @Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Receita> listarUltimasReceitasVisao(@Param("mes")  Integer mes,@Param("ano") Integer ano);

    List<Receita> buscarReceitaMembroData(@Param("mes") Long mes);

    List<Receita> listarPorIgreja(@Param("idIgreja") Long idIgreja);
}

