package br.com.assembleia.repositories;


import br.com.assembleia.entities.TipoDeDespesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDeDespesaRepository extends JpaRepository<TipoDeDespesa, Integer> {
}
