package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.auth.util.GeneralUtils;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.payload.response.UserResponse;
import org.mapstruct.*;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);

    @Mapping(target = "authorityNames", expression = "java(getAuthoritiesFromUser(user))")
    @Mapping(target = "password", expression = "java(new String(user.getPassword()))")
    @Mapping(target = "userId", source = "id")
    UserRedis toUserRedis(User user);

    default Set<String> getAuthoritiesFromUser(User user) {
        return GeneralUtils.getUserAuthorities(user);
    }

    @Mapping(target = "password", expression = "java(changeInfoRequest.password().getBytes())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User changeInfoRequest(ChangeInfoRequest changeInfoRequest, @MappingTarget User user);

    @Mapping(target = "authorityNames", expression = "java(java.util.Collections.emptySet())")
    UserInfo toUserInfo(User user);
}