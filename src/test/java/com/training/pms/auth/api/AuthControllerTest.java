package com.training.pms.auth.api;

import com.training.pms.auth.application.AuthService;
import com.training.pms.auth.dto.request.AuthRequest;
import com.training.pms.auth.dto.request.UserRequest;
import com.training.pms.auth.dto.response.AuthResponse;
import com.training.pms.auth.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void should_register_and_return_created_response() {
        UserRequest request = new UserRequest("alice", "alice@mail.com", "pwd");
        UserResponse response = new UserResponse("alice", "alice@mail.com", LocalDateTime.now());
        when(authService.register(request)).thenReturn(response);

        ResponseEntity<UserResponse> result = authController.register(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(authService).register(request);
    }

    @Test
    void should_login_and_return_ok_response() {
        AuthRequest request = new AuthRequest("alice", "pwd");
        AuthResponse response = new AuthResponse("jwt-token");
        when(authService.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthResponse> result = authController.login(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(response);
        verify(authService).authenticate(request);
    }
}
