
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
    BigDecimal valorReceitaPeriodo(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal listarReceitasParametro(@Param("recebido") Boolean recebido);

    List<Receita> listarReceitasMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal buscarReceitaGrafico(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal listarReceitasTipoMesAno(@Param("idTipoReceita") Long idTipoReceita, @Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Receita> listarUltimasReceitasVisao(@Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Receita> buscarReceitaMembroData(@Param("mes") Long mes);

    List<Receita> listarPorIgreja(@Param("idIgreja") Long idIgreja);

    List<Receita> listarReceitasMesAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("id") Long id);

    BigDecimal receitasRecebidasMeasAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idIgreja") Long id);

    BigDecimal receitasParametroMeasAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idIgreja") Long id, @Param("recebido") Boolean recebido);

    BigDecimal valorTotalReceitas(@Param("idIgreja") Long id);

    BigDecimal receitasParametroMeasAno(@Param("mes") Integer mes,@Param("ano") Integer ano, @Param("recebido") Boolean recebido);

    BigDecimal listarReceitas();
}

