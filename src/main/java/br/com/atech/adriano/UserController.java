package br.com.atech.adriano;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@GetMapping("/user")
	public List<User> getAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Long id) {
		return repository.findById(id).get();
	}

	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		return repository.save(user);
	}
	
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
