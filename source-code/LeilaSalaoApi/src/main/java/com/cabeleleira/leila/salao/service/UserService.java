package com.cabeleleira.leila.salao.service;

import com.cabeleleira.leila.salao.domain.User;
import com.cabeleleira.leila.salao.dto.CreateUserRequestDTO;
import com.cabeleleira.leila.salao.repository.UserRepository;
import com.cabeleleira.leila.salao.service.interfaces.IUserService;
import com.cabeleleira.leila.salao.shared.exceptions.EntityAlreadyExistsException;
import com.cabeleleira.leila.salao.shared.exceptions.NotFoundObjectException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não foi encontrado usuário");
                });
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    return new NotFoundObjectException("Não foi encontrado usuário");
                });
    }

    @Override
    @Transactional
    public User createRoleClient(CreateUserRequestDTO dto) {
        ensureEmailAlreadyExist(dto.email());
        User user = User.from(dto, passwordEncoder);
        return userRepository.save(user);
    }

    private void ensureEmailAlreadyExist(String email) {
        boolean isExist = userRepository.existsByEmail(email);
        if (isExist) {
            throw new EntityAlreadyExistsException("Existe um usuário utilizando esse email.");
        }
    };
}
