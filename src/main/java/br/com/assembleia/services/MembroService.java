package br.com.assembleia.services;

import br.com.assembleia.entities.Membro;
import br.com.assembleia.enums.EnumAtividades;
import br.com.assembleia.enums.EnumSexo;
import br.com.assembleia.enums.EnumSituacao;
import br.com.assembleia.repositories.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;

    public  Membro getById(Long id){
        return membroRepository.getOne(id);
    }

    public  Membro findOne(Long id){
        return membroRepository.findOne(id);
    }

    public void salvar(Membro cargo) {
        membroRepository.save(cargo);
    }

    public List<Membro> listarTodos() {
        return membroRepository.findAll();
    }

    public void deletar(Membro cargo) {
        membroRepository.delete(cargo);
    }

    public Integer buscarQtdTotalMembros() {
        return membroRepository.buscarQtdTotalMembros();
    }

    public Integer buscarQtdMembrosSituacao(EnumSituacao situacao) {
        return membroRepository.buscarQtdMembrosSituacao(situacao);
    }

    public Integer buscarQtdMembrosDizimistas() {
        return membroRepository.buscarQtdMembrosDizimistas();
    }

    public List<Membro> aniversariantesMes() {
        return membroRepository.aniversariantesMes();
    }

    public List<Membro> aniversariantesMesIgreja(Long idIgreja) {
        return membroRepository.aniversariantesMesIgreja(idIgreja);
    }


    public List<Membro> listarObreiros(EnumSexo sexo) {
        return membroRepository.listarObreiros(sexo);
    }

    public List<Membro> listarPorSexoCargo(EnumSexo sexo, String descricao) {
        return membroRepository.listarPorSexoCargo(sexo,descricao);
    }

    public List<Membro> listarPorSexoCargoCongregacao(EnumSexo sexo, String descricao, Long idIgreja) {
        return membroRepository.listarPorSexoCargoCongregacao(sexo,descricao,idIgreja);
    }

    public List<Membro> aniversariantesMesAnoRelatorio(int mes, int ano) {
        return membroRepository.aniversariantesMesAnoRelatorio(mes,ano);
    }

    public List<Membro> aniversariantesMesAnoIgrejaRelatorio(Long idIgreja, int mes, int ano){
        return membroRepository.aniversariantesMesAnoIgrejaRelatorio(idIgreja,mes,ano);
    }

    public List<Membro> listarPorIgreja(Long idIgreja) {
        return membroRepository.listarPorIgreja(idIgreja);
    }

    public Integer totalMembrosAtivos(Long idIgreja){
        return membroRepository.totalMembrosAtivos(idIgreja);
    }

    public Integer totalMembrosPorSexo(Long idIgreja,EnumSexo enumSexo){
        return membroRepository.totalMembrosPorSexo(idIgreja,enumSexo);
    }

    public Integer totalDizimistasPorParametro(Long idIgreja, Boolean dizimista){
        return membroRepository.totalDizimistasPorParametro(idIgreja, dizimista);
    }

    public Integer totalMembrosPorSexoGeral(EnumSexo sexo){
        return membroRepository.totalMembrosPorSexoGeral(sexo);
    }

    public Integer totalDizimistas(Boolean dizimista){
        return membroRepository.totalDizimistas(dizimista);
    }

    public List<Membro> listarPorAtividadeCongregacao(EnumAtividades atividade, Long idIgreja){
        return membroRepository.listarPorAtividadeCongregacao(atividade,idIgreja);
    }

}
