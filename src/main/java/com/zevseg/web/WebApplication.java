package com.zevseg.web;

import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Rank;
import com.zevseg.web.entity.Role;
import com.zevseg.web.entity.User;
import com.zevseg.web.repository.BranchRepository;
import com.zevseg.web.repository.RankRepository;
import com.zevseg.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

@SpringBootApplication
public class WebApplication {

	UserRepository userRepository;
	BranchRepository branchRepository;
	RankRepository rankRepository;
	PasswordEncoder passwordEncoder;

	@Autowired
	public WebApplication(UserRepository userRepository, BranchRepository branchRepository, RankRepository rankRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.branchRepository = branchRepository;
		this.rankRepository = rankRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void insertUser() {

		Optional<User> current = userRepository.findByEmail("admin@mail.com");
		List<Branch> branchList = branchRepository.findAll();
		List<Rank> rankList = rankRepository.findAll();

		if (!current.isPresent()) {
			User user = new User();
			user.setFirstname("Admin");
			user.setLastname("Admin");
			user.setRank(null);
			user.setEmail("admin@mail.com");
			user.setPassword(passwordEncoder.encode("Password123$"));
			user.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
			userRepository.save(user);
		}

		if(branchList.isEmpty()) {
			branchRepository.save(Branch.builder()
					.name("Хилийн застав")
					.build());
		}

		if(rankList.isEmpty()) {
			rankRepository.save(Rank.builder().name("Байлдагч").build());
			rankRepository.save(Rank.builder().name("Ахлах байлдагч").build());
			rankRepository.save(Rank.builder().name("Дэд түрүүч").build());
			rankRepository.save(Rank.builder().name("Түрүүч").build());
			rankRepository.save(Rank.builder().name("Ахлах түрүүч").build());
			rankRepository.save(Rank.builder().name("Дэд ахлагч").build());
			rankRepository.save(Rank.builder().name("Ахлагч").build());
			rankRepository.save(Rank.builder().name("Ахлах ахлагч").build());
			rankRepository.save(Rank.builder().name("Дэслэгч").build());
			rankRepository.save(Rank.builder().name("Ахлах дэслэгч").build());
			rankRepository.save(Rank.builder().name("Ахмад").build());
			rankRepository.save(Rank.builder().name("Хошууч").build());
			rankRepository.save(Rank.builder().name("Дэд хурандаа").build());
			rankRepository.save(Rank.builder().name("Хурандаа").build());
		}
	}
}
