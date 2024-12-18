package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.payload.response.UserResponse;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);

    @Mapping(target = "roleNames", expression = "java(rolesToRoleNames(user.getRoles()))")
    @Mapping(target = "password", expression = "java(new String(user.getPassword()))")
    @Mapping(target = "userId", source = "id")
    UserRedis toUserRedis(User user);

    default Set<String> rolesToRoleNames(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @Mapping(target = "password", expression = "java(changeInfoRequest.password().getBytes())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User changeInfoRequest(ChangeInfoRequest changeInfoRequest, @MappingTarget User user);

    UserInfo toUserInfo(User user);
}