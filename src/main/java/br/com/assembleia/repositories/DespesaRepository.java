package br.com.assembleia.repositories;

import br.com.assembleia.entities.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

    Despesa getById(@Param("id") Long id);

    BigDecimal valorDespesaPeriodo(@Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Despesa> listarDespesasMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano);

    List<Despesa> despesasPagarVisaoGeral(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal listarDespesasPagas();

    BigDecimal buscarDespesaGrafico(@Param("mes") Integer mes, @Param("ano") Integer ano);

    BigDecimal listarDespesasTipoMesAno(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("id") Long id);

    BigDecimal despesasDespesaMeasAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idIgreja") Long id);

    BigDecimal despesaParametroMeasAnoCongregacao(@Param("mes") Integer mes, @Param("ano") Integer ano, @Param("idIgreja") Long id, @Param("pago") Boolean pago);
}
