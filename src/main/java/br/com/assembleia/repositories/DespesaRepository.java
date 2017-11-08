package br.com.assembleia.repositories;

import br.com.assembleia.entities.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

    Despesa getById(@Param("id") Long id);

    BigDecimal valorDespesaPeriodo(Integer mes, Integer ano);

    List<Despesa> listarDespesasMesAno(@Param("mes") Integer mes, @Param("ano")Integer ano);

    List<Despesa> despesasPagarVisaoGeral(@Param("mes") Integer mes,@Param("ano") Integer ano);

    BigDecimal listarDespesasPagas();

    BigDecimal buscarDespesaGrafico(@Param("mes") Integer mes,@Param("ano") Integer ano);

    BigDecimal listarDespesasTipoMesAno(@Param("mes") Integer mes,@Param("ano") Integer ano,@Param("id") Long id);
}
