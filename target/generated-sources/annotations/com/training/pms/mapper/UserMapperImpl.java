package com.training.pms.mapper;

import com.training.pms.dto.UserRequest;
import com.training.pms.dto.UserResponse;
import com.training.pms.model.domain.User;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-28T16:52:50+0100",
    comments = "version: 1.7.0.Beta1, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        String username = null;
        String email = null;
        LocalDateTime createdAt = null;

        username = user.getUsername();
        email = user.getEmail();
        createdAt = user.getCreatedAt();

        UserResponse userResponse = new UserResponse( username, email, createdAt );

        return userResponse;
    }

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userRequest.username() );
        user.setEmail( userRequest.email() );
        user.setPassword( userRequest.password() );

        return user;
    }
}
