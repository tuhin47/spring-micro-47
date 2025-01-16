package me.tuhin47.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import me.tuhin47.auth.payload.request.LoginRequest;
import me.tuhin47.auth.payload.request.SignUpRequest;
import me.tuhin47.auth.payload.response.MenuDto;
import me.tuhin47.auth.payload.response.SignUpResponse;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.config.annotations.CurrentUser;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.core.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;
import java.util.stream.Stream;

@Tag(name = "Authentication API", description = "Operations related to user authentication and authorization")
public interface AuthController extends BaseController {

    @Operation(summary = "Authenticate user", description = "Authenticates a user with email and password")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User authenticated successfully"), @ApiResponse(responseCode = "400", description = "Invalid login credentials")})
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @Operation(summary = "Register user", description = "Registers a new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User registered successfully"), @ApiResponse(responseCode = "400", description = "Email address already in use!"), @ApiResponse(responseCode = "400", description = "Unable to generate QR code!")})
    ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_PRE_VERIFICATION_USER)")
    @Operation(summary = "Verify code", description = "Verifies the code for two-factor authentication")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Code verification successful"), @ApiResponse(responseCode = "400", description = "Invalid code")})
    ResponseEntity<?> verifyCode(@NotEmpty @RequestBody String code, @Parameter(hidden = true) @CurrentUser UserRedis userRedis);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @Operation(summary = "Get current user", description = "Retrieves information about the current authenticated user")
    @ApiResponse(responseCode = "200", description = "User details retrieved successfully")
    ResponseEntity<?> getCurrentUser(@Parameter(hidden = true) @CurrentUser UserRedis userRedis);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @Operation(summary = "Get all user summaries", description = "Retrieves summaries of all registered users")
    @ApiResponse(responseCode = "200", description = "User summaries retrieved successfully")
    ResponseEntity<Stream<UserInfo>> findAllUserSummaries(@Parameter(hidden = true) @CurrentUser UserRedis userRedis);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    ResponseEntity<Set<MenuDto>> getAuthMenus(UserRedis userRedis);
}
