package com.training.pms.auth.mapper;

import com.training.pms.auth.dto.request.UserRequest;
import com.training.pms.auth.dto.response.UserResponse;
import com.training.pms.model.domain.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void should_map_user_request_to_entity() {
        UserRequest request = new UserRequest("alice", "alice@mail.com", "pwd123");

        User result = mapper.toEntity(request);

        assertThat(result.getUsername()).isEqualTo("alice");
        assertThat(result.getEmail()).isEqualTo("alice@mail.com");
        assertThat(result.getPassword()).isEqualTo("pwd123");
    }

    @Test
    void should_map_user_entity_to_response() {
        User user = new User();
        user.setUsername("alice");
        user.setEmail("alice@mail.com");
        user.setCreatedAt(LocalDateTime.now());

        UserResponse result = mapper.toResponse(user);

        assertThat(result.username()).isEqualTo("alice");
        assertThat(result.email()).isEqualTo("alice@mail.com");
        assertThat(result.createdAt()).isEqualTo(user.getCreatedAt());
    }
}
