package br.com.assembleia.repositories;

import br.com.assembleia.entities.Despesa;
import br.com.assembleia.entities.DespesasTipoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

    Despesa getById(@Param("id") Long id);

    BigDecimal valorDespesaPeriodo(@Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Despesa> listarDespesasMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal listarDespesasPagas();

    BigDecimal buscarDespesaGrafico(@Param("mes") Integer mes, @Param("ano") Integer ano);

    List<DespesasTipoDTO> listarDespesasTipoMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal valorDespesasMesAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idIgreja") Long id);

    BigDecimal despesaParametroMeasAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idIgreja") Long id, @Param("pago") Boolean pago);

    List<Despesa> despesasMesAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idIgreja") Long id);

    BigDecimal valorTotalDespesasCongregacao(@Param("idIgreja") Long id);

    BigDecimal valorTotalDespesas();

    BigDecimal despesaParametroMeasAno(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("pago") Boolean pago);

    BigDecimal listarDespesas();

    BigDecimal listarDespesasParametro(@Param("pago") Boolean pago);

    List<DespesasTipoDTO> listarDespesasTipoMesAnoCongregracao(@Param("idIgreja") Long idIgreja, @Param("mes") Integer mes, @Param("ano") Integer ano);

}
