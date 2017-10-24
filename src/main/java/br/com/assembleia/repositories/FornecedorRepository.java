
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer>
{

    List<Fornecedor> listarPorIgreja(@Param("idIgreja") Long idIgreja);
}
