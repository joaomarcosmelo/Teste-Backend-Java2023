package br.com.atech.adriano;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		return repository.save(user);
	}
	
	@PutMapping("/user")
	public User updateUser(@RequestBody User user) {
		return repository.save(user);
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		return repository.findById(id).get();
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Long id) {
		User user = repository.findById(id).get();
		user.setAtive(false);
		repository.save(user);
	}
}
