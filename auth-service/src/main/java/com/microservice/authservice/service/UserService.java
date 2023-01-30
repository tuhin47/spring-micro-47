package com.microservice.authservice.service;

import com.microservice.authservice.model.ERole;
import com.microservice.authservice.model.Provider;
import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.User;
import com.microservice.authservice.security.user.OAuth2UserInfo;
import com.microservice.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    public User processOAuthPostLogin(OAuth2User oauthUser) {

        String email = oauthUser.<String>getAttribute("email");
        Optional<User> existUser = userRepository.findByUsername(email);

        if (existUser.isPresent()) {
            return existUser.get();
        }
        User newUser = new User();
        newUser.setUsername(email);
        newUser.setEmail(email);
        newUser.getRoles().add(new Role(ERole.ROLE_USER));
        newUser.setProvider(Provider.google);
        newUser.setPassword(RandomStringUtils.randomAlphanumeric(10));
        return userRepository.save(newUser);

    }

    public User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setProvider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setUsername(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setPassword(passwordEncoder.encode(oAuth2UserInfo.getId()));
        return userRepository.save(user);
    }


    public User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
