package me.tuhin47.auth.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import me.tuhin47.auth.dto.SignUpRequest;
import me.tuhin47.auth.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import me.tuhin47.auth.dto.LocalUser;
import me.tuhin47.auth.exception.UserAlreadyExistAuthenticationException;

/**
 * @author Chinna
 * @since 26/3/18
 */
public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	List<User> findAll();
}
