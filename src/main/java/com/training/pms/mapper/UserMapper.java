package com.training.pms.mapper;

import com.training.pms.dto.request.UserRequest;
import com.training.pms.dto.response.UserResponse;
import com.training.pms.model.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);

    User toEntity(UserRequest userRequest);
}