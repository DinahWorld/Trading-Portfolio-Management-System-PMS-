package com.training.pms.service;

import com.training.pms.dto.AuthRequest;
import com.training.pms.dto.AuthResponse;
import com.training.pms.dto.UserRequest;
import com.training.pms.dto.UserResponse;
import com.training.pms.mapper.UserMapper;
import com.training.pms.model.domain.User;
import com.training.pms.model.enums.Role;
import com.training.pms.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    void should_register_user_with_encoded_password_and_default_role() {
        UserRequest request = new UserRequest("alice", "alice@mail.com", "pwd123");
        User userToSave = new User();
        userToSave.setUsername("alice");
        userToSave.setEmail("alice@mail.com");
        User savedUser = new User();
        savedUser.setUsername("alice");
        savedUser.setEmail("alice@mail.com");
        savedUser.setCreatedAt(LocalDateTime.now());
        UserResponse expectedResponse = new UserResponse("alice", "alice@mail.com", savedUser.getCreatedAt());

        when(userRepository.findByUsername("alice")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("alice@mail.com")).thenReturn(Optional.empty());
        when(userMapper.toEntity(request)).thenReturn(userToSave);
        when(passwordEncoder.encode("pwd123")).thenReturn("encoded-password");
        when(userRepository.save(userToSave)).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(expectedResponse);

        UserResponse response = authService.register(request);

        assertThat(response).isEqualTo(expectedResponse);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("encoded-password");
        assertThat(userCaptor.getValue().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void should_fail_register_when_username_already_exists() {
        UserRequest request = new UserRequest("alice", "alice@mail.com", "pwd123");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("409 CONFLICT");

        verify(userRepository, never()).save(org.mockito.ArgumentMatchers.any(User.class));
    }

    @Test
    void should_authenticate_and_return_jwt_token() {
        AuthRequest request = new AuthRequest("alice", "password123");
        User user = new User();
        user.setUsername("alice");
        user.setPassword("$2a$encoded");
        user.setRole(Role.ADMIN);

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "$2a$encoded")).thenReturn(true);
        when(jwtService.generateToken(anyMap(), eq("alice"))).thenReturn("jwt-token");

        AuthResponse response = authService.authenticate(request);

        assertThat(response.token()).isEqualTo("jwt-token");
        verify(userRepository).findByUsername("alice");
        verify(passwordEncoder).matches("password123", "$2a$encoded");
        verify(jwtService).generateToken(anyMap(), eq("alice"));
    }

    @Test
    void should_fail_when_password_is_invalid() {
        AuthRequest request = new AuthRequest("bob", "bad-password");
        User user = new User();
        user.setUsername("bob");
        user.setPassword("$2a$encoded");
        user.setRole(Role.USER);

        when(userRepository.findByUsername("bob")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("bad-password", "$2a$encoded")).thenReturn(false);

        assertThatThrownBy(() -> authService.authenticate(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("401 UNAUTHORIZED");

        verify(jwtService, never()).generateToken(anyMap(), eq("bob"));
    }
}