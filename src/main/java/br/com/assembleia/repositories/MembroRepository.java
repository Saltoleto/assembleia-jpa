
package br.com.assembleia.repositories;

import br.com.assembleia.entities.Membro;
import br.com.assembleia.enums.EnumSexo;
import br.com.assembleia.enums.EnumSituacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MembroRepository extends JpaRepository<Membro, Integer>
{
     Integer buscarQtdTotalMembros();
     Integer buscarQtdMembrosSituacao(EnumSituacao situacao);
     Integer buscarQtdMembrosDizimistas();
     List<Membro> aniversariantesMes();
     List<Membro> listarObreiros(EnumSexo sexo);
     List<Membro> listarPorSexoCargo(EnumSexo sexo);
     List<Membro> aniversariantesRelatorio(Long mes);
}
