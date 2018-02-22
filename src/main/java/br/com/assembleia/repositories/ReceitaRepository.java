
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Receita;
import br.com.assembleia.entities.ReceitasTipoDTO;
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

    BigDecimal receitasParametroMeasAno(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("recebido") Boolean recebido);

    BigDecimal listarReceitas();


    List<ReceitasTipoDTO> listarReceitaTipoMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    List<ReceitasTipoDTO> listarReceitaTipoMesAnoCongregracao(@Param("idIgreja") Long idIgreja, @Param("mes") Integer mes, @Param("ano") Integer ano);

    List<ReceitasTipoDTO> listarReceitaMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    List<ReceitasTipoDTO> listarReceitaMesAnoCongregacao(@Param("idIgreja") Long idIgreja, @Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Receita> listarReceitasMembroAnaliticoMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("recebido") Boolean recebido);

    List<Receita> listarReceitasMembroAnaliticoMesAnoIgreja(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("IdIgreja") Long IdIgreja,@Param("recebido") Boolean recebido);

    BigDecimal receitasMembroParametroMeasAno(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("recebido") Boolean recebido);

    BigDecimal receitasMembroParametroMeasAnoIgreja(@Param("mes") Integer mes, @Param("ano") Integer ano,@Param("IdIgreja") Long idIgreja,@Param("recebido") Boolean recebido);

    List<Receita> listarMembroTipoIgrejaMesAno(@Param("tipo") String tipo,@Param("idIgreja") Long idIgreja, @Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Receita> listarMembroTipoMesAno(@Param("tipo") String tipo, @Param("mes") Integer mes, @Param("ano") Integer ano);
}

