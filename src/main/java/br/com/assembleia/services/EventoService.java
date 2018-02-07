package br.com.assembleia.services;

import br.com.assembleia.entities.Evento;
import br.com.assembleia.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> proximosEventosIgreja(Integer mes, Integer ano,Long idIgreja) {
        return eventoRepository.proximosEventosIgreja(mes, ano, idIgreja);
    }

    public List<Evento> proximosEventos(Integer mes, Integer ano) {
        return eventoRepository.proximosEventos(mes, ano);
    }

    public void salvar(Evento evento) {
        eventoRepository.save(evento);
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public void deletar(Evento evento) {
        eventoRepository.delete(evento);
    }

    public List<Evento> listarPorIgreja(Long idIgreja) {
        return eventoRepository.listarPorIgreja(idIgreja);
    }
}
