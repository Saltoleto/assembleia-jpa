/**
 * 
 */
package br.com.assembleia.controllers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.assembleia.entities.Usuario;
import br.com.assembleia.services.UserService;

/**
 * @author Siva
 *
 */
@ManagedBean
@SessionScoped
@Component
public class UserController {

	@Autowired
	private UserService userService;
	
	private List<Usuario> users = null;
	
	public UserController() {
		
	}
	public void setUsers(List<Usuario> users) {
		this.users = users;
	}
	
	public List<Usuario> getUsers() {
		if(users == null){
			users = userService.findAllUsers();
		}
		return users;
	}
}
