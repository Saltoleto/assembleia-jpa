
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlunoRepository extends JpaRepository<Aluno, Integer>
{

}
