package me.tuhin47.auth.controller;

import io.swagger.annotations.*;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Stream;

@Api(value = "Authentication API", tags = "AUTH-API", description = "Operations related to user authentication and authorization")
public interface AuthController extends BaseController {

    @ApiOperation(value = "Authenticate user", notes = "Authenticates a user with email and password")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User authenticated successfully"), @ApiResponse(code = 400, message = "Invalid login credentials")})
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @ApiOperation(value = "Register user", notes = "Registers a new user")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "User registered successfully"), @ApiResponse(code = 400, message = "Email address already in use!"), @ApiResponse(code = 400, message = "Unable to generate QR code!")})
    ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_PRE_VERIFICATION_USER)")
    @ApiOperation(value = "Verify code", notes = "Verifies the code for two-factor authentication")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Code verification successful"), @ApiResponse(code = 400, message = "Invalid code")})
    ResponseEntity<?> verifyCode(@NotEmpty @RequestBody String code, @ApiIgnore @CurrentUser UserRedis userRedis);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @ApiOperation(value = "Get current user", notes = "Retrieves information about the current authenticated user", authorizations = @Authorization("USER"))
    @ApiResponse(code = 200, message = "User details retrieved successfully")
    ResponseEntity<?> getCurrentUser(@ApiIgnore @CurrentUser UserRedis userRedis);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    @ApiOperation(value = "Get all user summaries", notes = "Retrieves summaries of all registered users")
    @ApiResponse(code = 200, message = "User summaries retrieved successfully")
    ResponseEntity<Stream<UserInfo>> findAllUserSummaries(@ApiIgnore @CurrentUser UserRedis userRedis);

    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_USER)")
    ResponseEntity<Set<MenuDto>> getAuthMenus(UserRedis userRedis);
}
