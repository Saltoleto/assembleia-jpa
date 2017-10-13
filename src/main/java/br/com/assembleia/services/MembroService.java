package br.com.assembleia.services;

import br.com.assembleia.entities.Membro;
import br.com.assembleia.enums.EnumSexo;
import br.com.assembleia.enums.EnumSituacao;
import br.com.assembleia.repositories.MembroRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;

    public void salvar(Membro cargo) {
        membroRepository.save(cargo);
    }

    public List<Membro> listarTodos() {
        return membroRepository.findAll();
    }

    public void deletar(Membro cargo) {
        membroRepository.delete(cargo);
    }
    
    public Integer buscarQtdTotalMembros(){
     return membroRepository.buscarQtdTotalMembros();
    }
    public Integer buscarQtdMembrosSituacao(EnumSituacao situacao){
        return membroRepository.buscarQtdMembrosSituacao(situacao);
    }
    
    public Integer buscarQtdMembrosDizimistas(){
        return membroRepository.buscarQtdMembrosDizimistas();
    }
    
    public List<Membro> aniversariantesMes(){
        return membroRepository.aniversariantesMes();
    }
    
    public List<Membro> listarObreiros(EnumSexo sexo){
        return membroRepository.listarObreiros(sexo);
    }
    
    public List<Membro> listarPorSexoCargo(EnumSexo sexo){
        return membroRepository.listarPorSexoCargo(sexo);
    }
    
    public List<Membro> aniversariantesRelatorio(Long mes) {
        return membroRepository.aniversariantesRelatorio(mes);
    }

}
