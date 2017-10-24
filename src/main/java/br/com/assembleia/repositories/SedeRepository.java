package br.com.assembleia.repositories;

import br.com.assembleia.entities.Congregacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SedeRepository extends JpaRepository<Congregacao, Integer> {

    public List<Congregacao> listarSedes();

}
