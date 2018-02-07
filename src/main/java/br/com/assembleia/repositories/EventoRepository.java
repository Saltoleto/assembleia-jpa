
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EventoRepository extends JpaRepository<Evento, Integer>
{

    List<Evento> listarPorIgreja(@Param("idIgreja") Long idIgreja);

    List<Evento> proximosEventosIgreja(@Param("mes") Integer mes, @Param("ano") Integer ano,@Param("idIgreja") Long idIgreja);

    List<Evento> proximosEventos(@Param("mes") Integer mes,@Param("ano") Integer ano);
}
