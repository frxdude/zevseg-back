package com.zevseg.web;

import com.zevseg.web.entity.Role;
import com.zevseg.web.entity.User;
import com.zevseg.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@SpringBootApplication
public class WebApplication {

	UserRepository userRepository;
	PasswordEncoder passwordEncoder;

	@Autowired
	public WebApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void insertUser() {

		Optional<User> current = userRepository.findByEmail("admin@mail.com");

		if (!current.isPresent()) {
			User user = new User();
			user.setFirstname("Admin");
			user.setLastname("Admin");
			user.setRank(null);
			user.setEmail("admin@mail.com");
			user.setPassword(passwordEncoder.encode("12345678"));
			user.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
			userRepository.save(user);
		}
	}
}
