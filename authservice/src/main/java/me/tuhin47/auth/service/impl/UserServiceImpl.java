package me.tuhin47.auth.service.impl;

import dev.samstevens.totp.secret.SecretGenerator;
import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.exception.OAuth2AuthenticationProcessingException;
import me.tuhin47.auth.exception.UserAlreadyExistAuthenticationException;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.mapper.UserMapper;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.payload.response.JwtAuthenticationResponse;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.auth.repo.RoleRepository;
import me.tuhin47.auth.repo.UserRepository;
import me.tuhin47.auth.security.oauth2.LocalUser;
import me.tuhin47.auth.security.oauth2.SocialProvider;
import me.tuhin47.auth.security.oauth2.user.OAuth2UserInfo;
import me.tuhin47.auth.security.oauth2.user.OAuth2UserInfoFactory;
import me.tuhin47.auth.service.UserService;
import me.tuhin47.auth.util.GeneralUtils;
import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.exception.common.UserServiceExceptions;
import me.tuhin47.jwt.TokenProvider;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretGenerator secretGenerator;
    private final TokenProvider tokenProvider;
    private final RedisUserService redisUserService;
    private final UserMapper userMapper;


    @Override
    @Transactional
    public UserRedis loadUserByUsername(final String email) throws UsernameNotFoundException {
        return findUserByEmail(email);
    }

    @Override
    @Transactional(value = "transactionManager")
    public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
        if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
            throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
        }
        User user = buildUser(signUpRequest);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    private User buildUser(final SignUpRequest formDTO) {
        User user = new User();
        user.setDisplayName(formDTO.getDisplayName());
        user.setEmail(formDTO.getEmail());
        user.setPassword(passwordEncoder.encode(formDTO.getPassword()).getBytes());
        final Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));
        user.setRoles(roles);
        user.setProvider(formDTO.getSocialProvider().getProviderType());
        user.setEnabled(true);
        user.setProviderUserId(formDTO.getProviderUserId());
        if (formDTO.isUsing2FA()) {
            user.setUsing2FA(true);
            user.setSecret(secretGenerator.generate());
        }
        return user;
    }

    @Override
    public UserRedis findUserByEmail(final String email) {
        Optional<UserRedis> redis = redisUserService.getUser(email);
        if (redis.isPresent()) {
            return redis.get();
        }
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        UserRedis userRedis = userMapper.toUserRedis(user);

        return redisUserService.saveLocalUser(userRedis);
    }

    @Override
    @Transactional
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (ObjectUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        } else if (ObjectUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SignUpRequest signUpRequest = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        User user = userRepository.findByEmail(signUpRequest.getEmail());
        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(signUpRequest);
        }

        LocalUser localUser = createLocalUser(user);

        return LocalUser.create(attributes, localUser);
    }

    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(), new String(user.getPassword()), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user.getId());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setDisplayName(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail()).addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> UserServiceExceptions.USER_NOT_FOUND.apply(id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public JwtAuthenticationResponse getJwtAuthenticationResponse(UserRedis localUser) {
        UserRedis userByEmail = findUserByEmail(localUser.getEmail());
        boolean authenticated = userByEmail != null;
        String jwt = tokenProvider.createToken(authenticated, localUser.getEmail());
        return new JwtAuthenticationResponse(jwt, authenticated, authenticated ? GeneralUtils.buildUserInfo(userByEmail) : null);
    }

    @Override
    public UserInfo updateUser(String id, ChangeInfoRequest user) {
        User existingUser = findUserById(id);
        return userMapper.toUserInfo(userMapper.changeInfoRequest(user, existingUser));
    }

    @Override
    public List<UserResponse> getAllUsers(String[] ids) {
        List<User> users;
        if (ids != null && ids.length > 0) {
            users = userRepository.findAllById(Arrays.asList(ids));
        } else {
            users = userRepository.findAll();
        }
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw UserServiceExceptions.USER_NOT_FOUND.apply(id);
        }
    }
}
