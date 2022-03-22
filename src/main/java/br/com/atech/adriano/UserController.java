package br.com.atech.adriano;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return repository.findAll();
	}
	
	@PostMapping("/user")
	public User createUser(@RequestBody User user) {
		
		this.validateUser(user);

		if(repository.findByName(user.getName()).size() > 0 ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um usuário com este nome.");
		}

		if(repository.findByEmail(user.getEmail()).size() > 0 ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um usuário com este e-mail.");
		}

		return repository.save(user);
	}
	
	@PutMapping("/user")
	public User updateUser(@RequestBody User user) {

		this.validateUser(user);
		
		//Existent name
		List<User> users = repository.findByName(user.getName());
		for (User u : users) {
			if(u.getId() != user.getId() && u.getName().equalsIgnoreCase(user.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe outro usuário com este nome.");
			}
		}

		//Existent e-mail
		users = repository.findByEmail(user.getEmail());
		for (User u : users) {
			if(u.getId() != user.getId() && u.getEmail().equalsIgnoreCase(user.getEmail())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe outro usuário com este e-mail.");
			}
		}

		return repository.save(user);
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		Optional<User> optional = repository.findById(id);
		
		if(optional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado.");
		}
		
		return optional.get();
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Long id) {
		Optional<User> optional = repository.findById(id);
		
		if(optional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado.");
		}
		
		User user = optional.get();
		user.setAtive(false);
		repository.save(user);
	}
	
	private void validateUser(User user) {
		if(user == null ) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário deve ser informado.");
		}
		
		if(StringUtils.isBlank(user.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome deve ser informado.");
		}

		if(StringUtils.isBlank(user.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O e-mail deve ser informado.");
		}

		if(StringUtils.isBlank(user.getPassword()) || user.getPassword().length() < 6) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha precisa ter pelo menos 6 caracteres.");
		}
	}
}
