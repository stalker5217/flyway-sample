package com.example.flywaysample.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@TestPropertySource("classpath:application-test.yml")
class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;

	@Container
	MariaDBContainer mariaDB = new MariaDBContainer(DockerImageName.parse("mariadb:10.3"));

	// @Container
	// static final MariaDBContainer mariaDB = new MariaDBContainer(DockerImageName.parse("mariadb:10.3"));

	@Test
	@DisplayName("사용자 조회 테스트")
	void findAllTest() {
		List<UserEntity> result = userRepository.findAll();
		assertThat(result).isNotEmpty();
	}
}
