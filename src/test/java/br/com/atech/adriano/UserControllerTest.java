package br.com.atech.adriano;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;


@SpringBootTest
public class UserControllerTest {
	
	@Autowired
	UserController controller;
	
//	@BeforeAll
//	public static void init(){
//		User user = new User();
//		user.setName("Adriano");
//		user.setEmail("adrianocarv@gmail.com");
//		user.setPassword("123456");
//
//		assertNull(user.getId());
//		user = controller.createUser(user);
//		assertNotNull(user.getId());
//	}
	
	@Test
	public void createUser() {
		User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("123456");

		assertNull(user.getId());
		user = controller.createUser(user);
		assertNotNull(user.getId());
	}
	
	@Test
	public void createExistentName() {
		final User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv2@gmail.com");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(controller.createUser(user)));
		assertEquals("400 BAD_REQUEST \"Já existe um usuário com este nome.\"", exception.getMessage());
	}

	@Test
	public void createExistentEmail() {
		final User user = new User();
		user.setName("Adriano2");
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(controller.createUser(user)));
		assertEquals("400 BAD_REQUEST \"Já existe um usuário com este email.\"", exception.getMessage());
	}

	@Test
	public void createUserWithoutName() {
		final User user = new User();
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(controller.createUser(user)));
		assertEquals("400 BAD_REQUEST \"O nome deve ser informado.\"", exception.getMessage());
	}

	@Test
	public void createUserWithoutEmail() {
		final User user = new User();
		user.setName("Adriano");
		user.setPassword("123456");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(controller.createUser(user)));
		assertEquals("400 BAD_REQUEST \"O e-mail deve ser informado.\"", exception.getMessage());
	}

	@Test
	public void createUserWithoutPassword() {
		final User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv@gmail.com");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(controller.createUser(user)));
		assertEquals("400 BAD_REQUEST \"A senha precisa ter pelo menos 6 caracteres.\"", exception.getMessage());
	}

	@Test
	public void createUserShortPassword() {
		final User user = new User();
		user.setName("Adriano");
		user.setEmail("adrianocarv@gmail.com");
		user.setPassword("12345");

		Exception exception = assertThrows(ResponseStatusException.class, () -> controller.createUser(controller.createUser(user)));
		assertEquals("400 BAD_REQUEST \"A senha precisa ter pelo menos 6 caracteres.\"", exception.getMessage());
	}
}
