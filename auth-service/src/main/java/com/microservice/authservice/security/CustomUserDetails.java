package com.microservice.authservice.security;

import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CustomUserDetails implements UserDetails, OAuth2User {

    private Integer id;
    private String email;
    private String password;
    private final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    private Map<String, Object> attributes;

    public CustomUserDetails(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public CustomUserDetails(User user) {
        this(user.getId(),user.getEmail(),user.getPassword());
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
    }

    public static CustomUserDetails create(User user, Map<String, Object> attributes) {
        CustomUserDetails userPrincipal = new CustomUserDetails(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }


    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return email;
    }
}
