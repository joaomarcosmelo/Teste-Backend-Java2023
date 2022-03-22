package br.com.atech.adriano;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
	
	@Autowired
	UserController controller;
	
	// ----------------------------------------
	// CREATE
	// ----------------------------------------
	
	@Test
	@Order(1)
	public void createUser() {
		User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("123456");

		assertNull(user.getId());
		user = controller.createUser(user);
		assertEquals(1,user.getId());
		
		User user2 = new User();
		user2.setName("André");
		user2.setEmail("andre@gmail.com");
		user2.setPassword("123456");

		assertNull(user2.getId());
		user2 = controller.createUser(user2);
		assertEquals(2,user2.getId());
	}
	
	@Test
	@Order(2)
	public void createExistentName() {
		final User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv2@gmail.com");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(user));
		assertEquals("400 BAD_REQUEST \"Já existe um usuário com este nome.\"", exception.getMessage());
	}

	@Test
	@Order(3)
	public void createExistentEmail() {
		final User user = new User();
		user.setName("Adriano2");
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(user));
		assertEquals("400 BAD_REQUEST \"Já existe um usuário com este e-mail.\"", exception.getMessage());
	}

	@Test
	public void createUserWithoutName() {
		final User user = new User();
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(user));
		assertEquals("400 BAD_REQUEST \"O nome deve ser informado.\"", exception.getMessage());
	}

	@Test
	public void createUserWithoutEmail() {
		final User user = new User();
		user.setName("Adriano");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(user));
		assertEquals("400 BAD_REQUEST \"O e-mail deve ser informado.\"", exception.getMessage());
	}

	@Test
	public void createUserWithoutPassword() {
		final User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv@gmail.com");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(user));
		assertEquals("400 BAD_REQUEST \"A senha precisa ter pelo menos 6 caracteres.\"", exception.getMessage());
	}

	@Test
	public void createUserShortPassword() {
		final User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("12345");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(user));
		assertEquals("400 BAD_REQUEST \"A senha precisa ter pelo menos 6 caracteres.\"", exception.getMessage());
	}

	// ----------------------------------------
	// READ
	// ----------------------------------------
	
	@Test
	public void readUser() {
		User user = controller.getUserById(1L);
		assertEquals("Adriano", user.getName());
	}

	@Test
	public void readNotExistentUser() {
		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.getUserById(3L));
		assertEquals("400 BAD_REQUEST \"Usuário não encontrado.\"", exception.getMessage());
	}

	// ----------------------------------------
	// UPDATE
	// ----------------------------------------
	
	@Test
	public void updateUser() {
		//Before
		User user2 = controller.getUserById(2L);
		assertEquals("André",user2.getName());
		assertEquals("andre@gmail.com",user2.getEmail());
		assertEquals("123456",user2.getPassword());

		//Update
		user2.setName("nome");
		user2.setEmail("email@gmail.com");
		user2.setPassword("654321");
		controller.updateUser(user2);
		
		//After
		user2 = controller.getUserById(2L);
		assertEquals("nome",user2.getName());
		assertEquals("email@gmail.com",user2.getEmail());
		assertEquals("654321",user2.getPassword());
		
		//Update as Before
		user2.setName("André");
		user2.setEmail("andre@gmail.com");
		user2.setPassword("123456");
		controller.updateUser(user2);
		
		//As Before
		user2 = controller.getUserById(2L);
		assertEquals("André",user2.getName());
		assertEquals("andre@gmail.com",user2.getEmail());
		assertEquals("123456",user2.getPassword());
	}
	
	@Test
	public void updateExistentName() {
		final User user2 = controller.getUserById(2L);
		user2.setName("Adriano");
		
		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.updateUser(user2));
		assertEquals("400 BAD_REQUEST \"Já existe outro usuário com este nome.\"", exception.getMessage());
	}

	@Test
	public void updateExistentEmail() {
		final User user2 = controller.getUserById(2L);
		user2.setEmail("adrianocarv@gmail.com");
		
		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.updateUser(user2));
		assertEquals("400 BAD_REQUEST \"Já existe outro usuário com este e-mail.\"", exception.getMessage());
	}

	@Test
	public void updateUserWithoutName() {
		final User user2 = controller.getUserById(2L);
		user2.setName("");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.updateUser(user2));
		assertEquals("400 BAD_REQUEST \"O nome deve ser informado.\"", exception.getMessage());
	}

	@Test
	public void updateUserWithoutEmail() {
		final User user2 = controller.getUserById(2L);
		user2.setEmail("");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.updateUser(user2));
		assertEquals("400 BAD_REQUEST \"O e-mail deve ser informado.\"", exception.getMessage());
	}

	@Test
	public void updateUserWithoutPassword() {
		final User user2 = controller.getUserById(2L);
		user2.setPassword("");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.updateUser(user2));
		assertEquals("400 BAD_REQUEST \"A senha precisa ter pelo menos 6 caracteres.\"", exception.getMessage());
	}

	@Test
	public void updateUserShortPassword() {
		final User user2 = controller.getUserById(2L);
		user2.setPassword("12345");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.updateUser(user2));
		assertEquals("400 BAD_REQUEST \"A senha precisa ter pelo menos 6 caracteres.\"", exception.getMessage());
	}

	// ----------------------------------------
	// DELETE
	// ----------------------------------------
	
	@Test
	public void deleteUser() {
		User user = controller.getUserById(1L);
		assertTrue(user.isAtive);
		
		controller.deleteUser(user.getId());

		user = controller.getUserById(1L);
		assertFalse(user.isAtive);
	}

	@Test
	public void deleteNotExistentUser() {
		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.deleteUser(3L));
		assertEquals("400 BAD_REQUEST \"Usuário não encontrado.\"", exception.getMessage());
	}


}
