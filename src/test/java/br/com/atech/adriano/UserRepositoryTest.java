package br.com.atech.adriano;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class  UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@Test
	public void createUser() {
		User user = new User();
		user.setName("Adriano Repo");
		user.setEmail("adrianocarvrepo@gmail.com");
		user.setPassword("123456");

		assertNull(user.getId());
		user = repository.save(user);
		assertNotNull(user.getId());
	}
}

