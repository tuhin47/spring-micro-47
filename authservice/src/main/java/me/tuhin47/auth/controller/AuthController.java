package me.tuhin47.auth.controller;

import io.swagger.annotations.*;
import me.tuhin47.auth.config.CurrentUser;
import me.tuhin47.auth.payload.request.LoginRequest;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.security.oauth2.LocalUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Api(value = "Authentication API", tags = "AUTH-API" ,description = "Operations related to user authentication and authorization")
public interface AuthController {

    @ApiOperation(value = "Authenticate user", notes = "Authenticates a user with email and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User authenticated successfully"),
            @ApiResponse(code = 400, message = "Invalid login credentials")
    })
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @ApiOperation(value = "Register user", notes = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User registered successfully"),
            @ApiResponse(code = 400, message = "Email address already in use!"),
            @ApiResponse(code = 400, message = "Unable to generate QR code!")
    })
    ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest);

    @PostMapping("/verify")
    @PreAuthorize("hasRole('PRE_VERIFICATION_USER')")
    @ApiOperation(value = "Verify code", notes = "Verifies the code for two-factor authentication")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Code verification successful"),
            @ApiResponse(code = 400, message = "Invalid code")
    })
    ResponseEntity<?> verifyCode(@NotEmpty @RequestBody String code, @ApiIgnore @CurrentUser LocalUser user);

    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Get current user", notes = "Retrieves information about the current authenticated user", authorizations = @Authorization("USER"))
    @ApiResponse(code = 200, message = "User details retrieved successfully")
    ResponseEntity<?> getCurrentUser(@ApiIgnore @CurrentUser LocalUser user);

    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Get all user summaries", notes = "Retrieves summaries of all registered users")
    @ApiResponse(code = 200, message = "User summaries retrieved successfully")
    ResponseEntity<?> findAllUserSummaries(@ApiIgnore @CurrentUser LocalUser localUser);

    @ApiOperation(value = "Get public content", notes = "Retrieves public content")
    @ApiResponse(code = 200, message = "Public content retrieved successfully")
    ResponseEntity<?> getContent();

    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Get user content", notes = "Retrieves content specific to authenticated users")
    @ApiResponse(code = 200, message = "User content retrieved successfully")
    ResponseEntity<?> getUserContent();

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Get admin content", notes = "Retrieves content specific to administrators")
    @ApiResponse(code = 200, message = "Admin content retrieved successfully")
    ResponseEntity<?> getAdminContent();

    @PreAuthorize("hasRole('MODERATOR')")
    ResponseEntity<?> getModeratorContent();
}
