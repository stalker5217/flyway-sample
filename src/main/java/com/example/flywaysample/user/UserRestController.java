package com.example.flywaysample.user;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flywaysample.support.Company;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("{target}/api/v1/users")
@Slf4j
public class UserRestController {
	private final UserRepository userRepository;

	public UserRestController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping
	public ResponseEntity<Void> createUser(
		@PathVariable("target") Company target,
		String name
	) {
		log.info("{}", target);

		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);

		userRepository.save(userEntity);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> readUser(
		@PathVariable("target") Company target,
		@PathVariable("id") Long id
	) {
		log.info("{}", target);

		UserEntity userEntity = userRepository
			.findById(id)
			.orElseThrow(NoSuchElementException::new);

		return ResponseEntity.ok(userEntity.getName());
	}
}
