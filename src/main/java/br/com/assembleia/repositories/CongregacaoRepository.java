
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Congregacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CongregacaoRepository extends JpaRepository<Congregacao, Integer>
{

}
