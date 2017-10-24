package br.com.assembleia.repositories;

import br.com.assembleia.entities.Congregacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CongregacaoRepository extends JpaRepository<Congregacao, Integer> {

    Congregacao buscarSede();

    List<Congregacao> listarCongregacoes();

    Congregacao getByid(@Param("id") Long id);

}
