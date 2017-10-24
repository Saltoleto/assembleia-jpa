package br.com.assembleia.services;

import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.repositories.CongregacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CongregacaoService {

    @Autowired
    private CongregacaoRepository congregacaoRepository;

    public void salvar(Congregacao cargo) {
        congregacaoRepository.save(cargo);
    }

    public List<Congregacao> listarTodos() {
        return congregacaoRepository.findAll();
    }

    public List<Congregacao> listarCongregacoes() {
        return congregacaoRepository.listarCongregacoes();
    }

    public void deletar(Congregacao cargo) {
        congregacaoRepository.delete(cargo);
    }

    public Congregacao buscarSede() {
        return congregacaoRepository.buscarSede();
    }

    public Congregacao getById(Long id) {
        return congregacaoRepository.getByid(id);
    }

}
