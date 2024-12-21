package me.tuhin47.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.tuhin47.auth.model.User;
import me.tuhin47.auth.payload.request.ChangeInfoRequest;
import me.tuhin47.auth.payload.response.UserInfo;
import me.tuhin47.core.BaseController;
import me.tuhin47.payload.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(value = "USER API", tags = "USER-API", description = "Operations related to user information")
public interface UserController extends BaseController {

    @ApiOperation(value = "Get all users", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you requested is forbidden"),
        @ApiResponse(code = 404, message = "The resource you requested could not be found")
    })
    ResponseEntity<List<UserResponse>> getAllUsers(String[] ids);

    @ApiOperation(value = "Get user by id", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved user"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you requested is forbidden"),
        @ApiResponse(code = 404, message = "The resource you requested could not be found")
    })
    ResponseEntity<UserInfo> getUserById(@PathVariable String id);

    @ApiOperation(value = "Update an existing user", response = User.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User updated successfully"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "You are not authorized to update a user"),
        @ApiResponse(code = 403, message = "Accessing the resource you requested is forbidden"),
        @ApiResponse(code = 404, message = "The resource you requested could not be found")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        //TODO: Admin/Self
    ResponseEntity<UserInfo> updateUser(@PathVariable String id, @RequestBody ChangeInfoRequest user);

    @ApiOperation(value = "Delete a user", response = Void.class)
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "User deleted successfully"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "You are not authorized to delete a user"),
        @ApiResponse(code = 403, message = "Accessing the resource you requested is forbidden"),
        @ApiResponse(code = 404, message = "The resource you requested could not be found")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<Void> deleteUser(@PathVariable String id);
}