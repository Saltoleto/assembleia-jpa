
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Membro;
import br.com.assembleia.enums.EnumSexo;
import br.com.assembleia.enums.EnumSituacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MembroRepository extends JpaRepository<Membro, Integer> {
    
    Integer buscarQtdTotalMembros();

    Integer buscarQtdMembrosSituacao(EnumSituacao situacao);

    Integer buscarQtdMembrosDizimistas();

    List<Membro> aniversariantesMes();

    List<Membro> listarObreiros(EnumSexo sexo);

    List<Membro> listarPorSexoCargo(EnumSexo sexo);

    List<Membro> aniversariantesRelatorio(Long mes);

    List<Membro> listarPorIgreja(@Param("idIgreja") Long idIgreja);
}
