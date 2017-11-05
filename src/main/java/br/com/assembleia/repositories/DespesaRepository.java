package br.com.assembleia.repositories;

import br.com.assembleia.entities.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

    void salvar(Despesa despesa) throws IllegalArgumentException;

    List<Despesa> listarTodos();

    void editar(Despesa despesa);

    void deletar(Despesa despesa);

    Despesa getById(final Long id);

    BigDecimal valorDespesaPeriodo(Integer mes, Integer ano);

    List<Despesa> listarDespesasMesAno(Integer mes, Integer ano);

    List<Despesa> despesasPagarVisaoGeral(Integer mes, Integer ano);

    BigDecimal listarDespesasPagas();

    BigDecimal buscarDespesaGrafico(Integer mes, Integer ano);

    BigDecimal listarDespesasTipoMesAno(Integer mes, Integer ano, Long id);
}
