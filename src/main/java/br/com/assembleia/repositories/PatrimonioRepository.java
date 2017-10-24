package br.com.assembleia.repositories;

import br.com.assembleia.entities.Patrimonio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Integer> {

    BigDecimal valorPatrimonio();

    List<Patrimonio> listarPorIgreja(@Param("idIgreja") Long idIgreja);
}
