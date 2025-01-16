package me.tuhin47.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.tuhin47.auth.command.RolePayload;
import me.tuhin47.auth.payload.response.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Role API", description = "Operations related to roles")
public interface RoleController {

    @Operation(summary = "Add a new role", description = "Creates a new role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Role created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<RoleDto> addRole(@RequestBody @Schema(implementation = RolePayload.class) RolePayload payload);

    @Operation(summary = "Edit an existing role", description = "Updates an existing role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role updated successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    ResponseEntity<RoleDto> editRole(@Parameter(description = "The ID of the role to be updated", required = true)
                                     @PathVariable Long roleId,
                                     @RequestBody @Schema(implementation = RolePayload.class) RolePayload payload);

    @Operation(summary = "Get all roles", description = "Retrieves all roles")
    @ApiResponse(responseCode = "200", description = "Roles retrieved successfully")
    ResponseEntity<List<RoleDto>> getAllRoles();

    @Operation(summary = "Get role by ID", description = "Retrieves a role by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    ResponseEntity<RoleDto> getRoleById(@Parameter(description = "The ID of the role to be retrieved", required = true)
                                        @PathVariable Long roleId);
}