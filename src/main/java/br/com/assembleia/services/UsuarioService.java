package br.com.assembleia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.assembleia.entities.Usuario;
import br.com.assembleia.repositories.UsuarioRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public List<Usuario> listarTodos() {
        return userRepository.findAll();
    }

    public void salvar(Usuario usuario) throws IllegalArgumentException {
        userRepository.save(usuario);
    }

    public void deletar(Usuario usuario) {
        userRepository.delete(usuario);
    }

    public Usuario findByLogin(String login, String senha) {
        return userRepository.findByLogin(login, senha);
    }

}
