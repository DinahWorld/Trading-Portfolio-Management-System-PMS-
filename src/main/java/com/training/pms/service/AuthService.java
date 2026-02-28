package com.training.pms.service;

import com.training.pms.core.exceptions.AuthUnauthorizedException;
import com.training.pms.core.exceptions.UserNotFoundException;
import com.training.pms.dto.request.AuthRequest;
import com.training.pms.dto.request.UserRequest;
import com.training.pms.dto.response.AuthResponse;
import com.training.pms.dto.response.UserResponse;
import com.training.pms.mapper.UserMapper;
import com.training.pms.model.domain.User;
import com.training.pms.model.enums.Role;
import com.training.pms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponse register(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.username()).isPresent()) {
            throw new UserNotFoundException("Username already exists");
        }
        if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new UserNotFoundException("Email already exists");
        }

        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Transactional
    public AuthResponse authenticate(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.username())
                .orElseThrow(() -> new AuthUnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            throw new AuthUnauthorizedException("Invalid credentials");
        }

        String token = jwtService.generateToken(Map.of("role", String.valueOf(user.getRole())), user.getUsername());
        return new AuthResponse(token);
    }
}