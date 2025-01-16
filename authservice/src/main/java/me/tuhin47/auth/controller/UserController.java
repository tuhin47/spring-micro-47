package me.tuhin47.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.core.BaseController;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "USER API", description = "Operations related to user information")
public interface UserController extends BaseController {

    @Operation(summary = "Get all users", description = "Fetches a list of all users", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you requested is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you requested could not be found")
    })
    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_ADMIN)")
    ResponseEntity<List<UserResponse>> getAllUsers(String[] ids);

    @Operation(summary = "Get user by id", description = "Fetches user details by ID", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfo.class))),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you requested is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you requested could not be found")
    })
    ResponseEntity<UserInfo> getUserById(@PathVariable String id);

    @Operation(summary = "Update an existing user", description = "Updates user details", responses = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfo.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to update a user"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you requested is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you requested could not be found")
    })
    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_ADMIN)")
    ResponseEntity<UserInfo> updateUser(@PathVariable String id, @RequestBody ChangeInfoRequest user);

    @Operation(summary = "Delete a user", description = "Deletes a user by ID", responses = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to delete a user"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you requested is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you requested could not be found")
    })
    @PreAuthorize("hasAuthority(T(me.tuhin47.utils.RoleUtils).ROLE_ADMIN)")
    ResponseEntity<Void> deleteUser(@PathVariable String id);
}
