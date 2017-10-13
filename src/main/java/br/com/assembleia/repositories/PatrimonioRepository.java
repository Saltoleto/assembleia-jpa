package br.com.assembleia.repositories;

import br.com.assembleia.entities.Patrimonio;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Integer> {

    BigDecimal valorPatrimonio();
}
