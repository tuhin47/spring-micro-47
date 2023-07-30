package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.model.User;
import me.tuhin47.payload.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);
}