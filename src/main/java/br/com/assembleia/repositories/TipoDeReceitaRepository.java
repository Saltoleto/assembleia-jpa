package br.com.assembleia.repositories;

import br.com.assembleia.entities.TipoDeReceita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDeReceitaRepository extends JpaRepository<TipoDeReceita, Integer> {
}
