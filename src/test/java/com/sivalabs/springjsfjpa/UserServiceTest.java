package com.sivalabs.springjsfjpa;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.assembleia.entities.Usuario;
import br.com.assembleia.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext.xml")
public class UserServiceTest
{
	@Autowired
	private UserService userService;
	
	@Test
	public void testFindAllusers() {
		List<Usuario> users = userService.findAllUsers();
		Assert.assertNotNull(users);
		for (Usuario user : users) {
			System.err.println(user);
		}
	}
}
