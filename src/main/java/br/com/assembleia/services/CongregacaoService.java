package br.com.assembleia.services;

import br.com.assembleia.entities.Congregacao;
import br.com.assembleia.repositories.CongregacaoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void deletar(Congregacao cargo) {
        congregacaoRepository.delete(cargo);
    }

}
