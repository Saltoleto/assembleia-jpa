
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartamentoRepository extends JpaRepository<Departamento, Integer>
{

}
