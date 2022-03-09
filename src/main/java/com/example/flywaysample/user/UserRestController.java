package com.example.flywaysample.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
	private final UserRepository userRepository;

	public UserRestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping
	public ResponseEntity<Void> createUser(String name) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);

		userRepository.save(userEntity);

		return ResponseEntity.ok().build();
	}
}
