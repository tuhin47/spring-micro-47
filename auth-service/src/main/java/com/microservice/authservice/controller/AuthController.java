package com.microservice.authservice.controller;

import com.microservice.authservice.exception.RefreshTokenException;
import com.microservice.authservice.exception.RoleException;
import com.microservice.authservice.jwt.JwtUtils;
import com.microservice.authservice.model.ERole;
import com.microservice.authservice.model.RefreshToken;
import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.request.LoginRequest;
import com.microservice.authservice.payload.request.SignUpRequest;
import com.microservice.authservice.payload.request.TokenRefreshRequest;
import com.microservice.authservice.payload.response.MessageResponse;
import com.microservice.authservice.payload.response.TokenRefreshResponse;
import com.microservice.authservice.service.RefreshTokenService;
import com.microservice.authservice.service.RoleService;
import com.microservice.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final RoleService roleService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

        String username = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if(userService.existsByUsername(username)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if(userService.existsByEmail(email)){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        if (strRoles != null) {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = null;

                        if(roleService.findByName(ERole.ROLE_ADMIN).isEmpty()){
                            adminRole = new Role(ERole.ROLE_ADMIN);
                        }else{
                            adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RoleException("Error: Admin Role is not found."));
                        }

                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = null;

                        if(roleService.findByName(ERole.ROLE_USER).isEmpty()){
                            userRole = new Role(ERole.ROLE_USER);
                        }else{
                            userRole = roleService.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RoleException("Error: User Role is not found."));
                        }

                        roles.add(userRole);
                }
            });
        }else{
            roleService.findByName(ERole.ROLE_USER).ifPresentOrElse(roles::add, () -> roles.add(new Role(ERole.ROLE_USER)));
        }

        user.setRoles(roles);
        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(jwtUtils.getJwtResponseResponseEntity(authentication));
    }


    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {

        String requestRefreshToken = request.getRefreshToken();

        return getRefreshTokenResponse(requestRefreshToken);
    }

    @GetMapping("/refreshtoken")
    public ResponseEntity<?> getRefreshtoken(@RequestParam("token") String requestRefreshToken) {

        return getRefreshTokenResponse(requestRefreshToken);
    }

    private ResponseEntity<?> getRefreshTokenResponse(String refreshToken) {
        RefreshToken token = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenException(refreshToken + "Refresh token is not in database!"));

        RefreshToken deletedToken = refreshTokenService.verifyExpiration(token);

        User userRefreshToken = deletedToken.getUser();

        String newToken = jwtUtils.generateTokenFromUsername(userRefreshToken.getUsername());

        return ResponseEntity.ok(new TokenRefreshResponse(newToken, refreshToken));
    }
}
