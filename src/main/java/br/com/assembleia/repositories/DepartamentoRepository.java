
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DepartamentoRepository extends JpaRepository<Departamento, Integer>
{

    List<Departamento> listarPorIgreja(@Param("idIgreja") Long idIgreja);
}
