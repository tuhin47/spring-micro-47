package me.tuhin47.auth.service;

import jakarta.validation.constraints.NotBlank;
import me.tuhin47.auth.exception.UserAlreadyExistAuthenticationException;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.payload.response.JwtAuthenticationResponse;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.auth.security.oauth2.LocalUser;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService extends UserDetailsService {

    User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    String getEncodedPassword(@NotBlank byte[] password);

    UserRedis findUserByEmail(String email);

    UserInfo findUserInfoById(String id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

    List<User> findAll();

    JwtAuthenticationResponse getJwtAuthenticationResponse(String email);

    List<UserResponse> getAllUsers(String[] ids);

    UserInfo updateUser(String id, ChangeInfoRequest user);

    void deleteUser(String id);
}


