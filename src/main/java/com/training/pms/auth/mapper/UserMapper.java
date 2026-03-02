package com.training.pms.auth.mapper;

import com.training.pms.auth.dto.request.UserRequest;
import com.training.pms.auth.dto.response.UserResponse;
import com.training.pms.model.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);

    User toEntity(UserRequest userRequest);
}