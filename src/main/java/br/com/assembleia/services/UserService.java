/**
 * 
 */
package br.com.assembleia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.assembleia.entities.Usuario;
import br.com.assembleia.repositories.UserRepository;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class UserService 
{
	@Autowired private UserRepository userRepository;
	
	public List<Usuario> findAllUsers() {
		return userRepository.findAll();
	}
	
}
