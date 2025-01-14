package me.tuhin47.auth.service.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.auth.config.TransactionEmailEvent;
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
import me.tuhin47.config.exception.common.AuthServiceExceptions;
import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.config.utils.MyProtoTypeBean;
import me.tuhin47.jwt.TokenProvider;
import me.tuhin47.payload.response.UserResponse;
import me.tuhin47.utils.RoleUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    //    private final SecretGenerator secretGenerator;
    //    private final QrDataFactory qrDataFactory;
    //    private final QrGenerator qrGenerator;
    //    private final AppProperties appProperties;
    private final TokenProvider tokenProvider;
    private final RedisUserService redisUserService;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MyRequestBean myRequestBean;
    private final ApplicationContext applicationContext;

    @Override
    @Transactional
    public UserRedis loadUserByUsername(final String email) throws UsernameNotFoundException {
        return findUserByEmail(email);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
        }

        User user = userMapper.toEntity(signUpRequest, this);
        setUserRole(user);
        user = userRepository.save(user);

        qrGenerator(signUpRequest, user);

        applicationEventPublisher.publishEvent(new TransactionEmailEvent(this, user.getEmail(), "Your registration is successfully completed", "Successful Registration"));

        return user;
    }

    private void qrGenerator(SignUpRequest signUpRequest, User user) {
        /*try {
            if (signUpRequest.isUsing2FA()) {
                QrData data = qrDataFactory.newBuilder()
                                           .label(user.getEmail())
                                           .secret(user.getSecret())
                                           .issuer(appProperties.getConfig().getQrIssuer())
                                           .build();
                log.debug("Generate the QR code image data as a base64 string which can be used in an <img> tag ");
                String qrCodeImage = getDataUriForImage(qrGenerator.generate(data), qrGenerator.getImageMimeType());
            }
        } catch (QrGenerationException e) {
            log.error("QR Generation Exception Occurred", e);
            user.setUsing2FA(false);
        }*/
    }

    private void setUserRole(User user) {
        final Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(RoleUtils.ROLE_USER));
        user.setRoles(roles);
    }

    @Override
    public String getEncodedPassword(@NotBlank byte[] password) {
        return passwordEncoder.encode(new String(password));
    }

    @Override
    public UserRedis findUserByEmail(final String email) {
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
        return new LocalUser(user, getAuthorities(user));
    }

    private List<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return UserRedis.getSimpleGrantedAuthorities(roles);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setDisplayName(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setProviderUserId(oAuth2UserInfo.getId());
        signUpRequest.setDisplayName(oAuth2UserInfo.getName());
        signUpRequest.setEmail(oAuth2UserInfo.getEmail());
        signUpRequest.setSocialProvider(GeneralUtils.toSocialProvider(registrationId));
        signUpRequest.setPassword("changeit");
        signUpRequest.setMatchingPassword("changeit");

        return signUpRequest;
    }

    @Override
    public UserInfo findUserInfoById(String id) {
        return userMapper.toUserInfo(findUserById(id));
    }

    private User findUserById(String id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public JwtAuthenticationResponse getJwtAuthenticationResponse(String email) {
        UserRedis userByEmail = findUserByEmail(email);
        boolean authenticated = userByEmail != null;
        String jwt = tokenProvider.createToken(authenticated, email);
        return new JwtAuthenticationResponse(jwt, authenticated, authenticated ? GeneralUtils.buildUserInfo(userByEmail) : null);
    }

    @Override
    @Transactional
    public UserInfo updateUser(String id, ChangeInfoRequest user) {
        User existingUser = findUserById(id);
        User changed = userRepository.save(userMapper.changeInfoRequest(user, existingUser, this));
        return userMapper.toUserInfo(changed);
    }

    @Override
    public List<UserResponse> getAllUsers(String[] ids) {
        List<User> users;
        var myProtoTypeBean = applicationContext.getBean(MyProtoTypeBean.class);
        log.info("getAllUsers {}", myRequestBean.getData().get("ids"));
        if (ids != null && ids.length > 0) {
            users = userRepository.findAllById(Arrays.asList(ids));
        } else {
            users = userRepository.findAll();
        }
        myProtoTypeBean.showSpentTime();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw AuthServiceExceptions.USER_NOT_FOUND.apply(id).get();
        }
    }
}
