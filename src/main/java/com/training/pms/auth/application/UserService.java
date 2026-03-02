package com.training.pms.auth.application;

import com.training.pms.auth.dto.request.UserRequest;
import com.training.pms.auth.dto.response.UserResponse;
import com.training.pms.auth.mapper.UserMapper;
import com.training.pms.auth.repository.UserRepository;
import com.training.pms.model.domain.User;
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