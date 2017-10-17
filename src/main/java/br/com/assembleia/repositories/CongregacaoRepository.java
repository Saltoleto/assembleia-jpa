package br.com.assembleia.repositories;

import br.com.assembleia.entities.Congregacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CongregacaoRepository extends JpaRepository<Congregacao, Integer> {

    public List<Congregacao> listarSedes();

}
