
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer>
{

}
