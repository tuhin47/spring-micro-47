package me.tuhin47.auth.service;

import me.tuhin47.auth.exception.UserAlreadyExistAuthenticationException;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.payload.response.JwtAuthenticationResponse;
import me.tuhin47.auth.security.oauth2.LocalUser;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService extends UserDetailsService {

	User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    UserRedis findUserByEmail(String email);

	Optional<User> findUserById(String id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	List<User> findAll();

    JwtAuthenticationResponse getJwtAuthenticationResponse(UserRedis userRedis);

    List<UserResponse> getAllUsers(String[] ids);

    User updateUser(String id, User user);

    void deleteUser(String id);
}


