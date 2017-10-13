
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer>
{

}
