package com.cabeleleira.leila.salao.config;

import com.cabeleleira.leila.salao.domain.User;
import com.cabeleleira.leila.salao.enums.UsersRole;
import com.cabeleleira.leila.salao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;

@Configuration
public class AdminConfig implements CommandLineRunner {
    @Value("${leila.admin.email}")
    private String adminEmail;

    @Value("${leila.admin.password}")
    private String adminPassword;

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AdminConfig(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        User admin = new User(
                null,
                adminEmail,
                passwordEncoder.encode(adminPassword),
                UsersRole.ADMIN,
                true,
                Instant.now()
        );

        userRepository.save(admin);
    }
}
