
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfessorRepository extends JpaRepository<Professor, Integer>
{

}
