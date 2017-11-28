package br.com.assembleia.repositories;

import br.com.assembleia.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

    List<Curso> listarPorIgreja(@Param("idIgreja") Long idIgreja);
}
