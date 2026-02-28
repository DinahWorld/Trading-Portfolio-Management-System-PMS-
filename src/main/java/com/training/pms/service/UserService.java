package com.training.pms.service;

import com.training.pms.dto.UserRequest;
import com.training.pms.dto.UserResponse;
import com.training.pms.mapper.UserMapper;
import com.training.pms.model.domain.User;
import com.training.pms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        User savedUser = repository.save(user);
        return mapper.toResponse(savedUser);
    }
}