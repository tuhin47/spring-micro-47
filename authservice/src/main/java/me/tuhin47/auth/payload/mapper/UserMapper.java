package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.auth.service.UserService;
import me.tuhin47.auth.util.GeneralUtils;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.payload.response.UserResponse;
import org.mapstruct.*;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);

    @Mapping(target = "authorityNames", expression = "java(getAuthoritiesFromUser(user))")
    @Mapping(target = "password", expression = "java(user.getPassword())")
    @Mapping(target = "userId", source = "id")
    UserRedis toUserRedis(User user);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", expression =
        "java(changeInfoRequest.getPassword() != null ? userService.getEncodedPassword(changeInfoRequest.getPassword()) : user.getPassword())"
    )
    User changeInfoRequest(ChangeInfoRequest changeInfoRequest, @MappingTarget User user, UserService userService);

    @Mapping(target = "authorityNames", expression = "java(getAuthoritiesFromUser(user))")
    UserInfo toUserInfo(User user);

    default Set<String> getAuthoritiesFromUser(User user) {
        return GeneralUtils.getUserAuthorities(user);
    }

    @Mapping(target = "provider", expression = "java(signUpRequest.getSocialProvider().getProviderType())")
    @Mapping(target = "password", expression =
        "java(signUpRequest.getPassword() != null ? userService.getEncodedPassword(signUpRequest.getPassword()) : user.getPassword())"
    )
    User toEntity(SignUpRequest signUpRequest, UserService userService);
}