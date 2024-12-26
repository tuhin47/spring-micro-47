package me.tuhin47.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.tuhin47.auth.command.RolePayload;
import me.tuhin47.auth.payload.response.RoleDto;
import me.tuhin47.core.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(value = "Role API", tags = "ROLE-API", description = "Operations related to roles")
public interface RoleController extends BaseController {

    @ApiOperation(value = "Add a new role", notes = "Creates a new role")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Role created successfully"),
        @ApiResponse(code = 400, message = "Invalid input")
    })
    ResponseEntity<RoleDto> addRole(@RequestBody RolePayload payload);

    @ApiOperation(value = "Edit an existing role", notes = "Updates an existing role")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Role updated successfully"),
        @ApiResponse(code = 404, message = "Role not found")
    })
    ResponseEntity<RoleDto> editRole(@PathVariable Long roleId, @RequestBody RolePayload payload);

    @ApiOperation(value = "Get all roles", notes = "Retrieves all roles")
    @ApiResponse(code = 200, message = "Roles retrieved successfully")
    ResponseEntity<List<RoleDto>> getAllRoles();

    @ApiOperation(value = "Get role by ID", notes = "Retrieves a role by its ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Role retrieved successfully"),
        @ApiResponse(code = 404, message = "Role not found")
    })
    ResponseEntity<RoleDto> getRoleById(@PathVariable Long roleId);
}
