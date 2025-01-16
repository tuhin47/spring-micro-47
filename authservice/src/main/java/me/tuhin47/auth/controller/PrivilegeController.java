package me.tuhin47.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.tuhin47.auth.command.PrivilegePayload;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.payload.response.PrivilegeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Privilege API", description = "Operations related to privileges")
public interface PrivilegeController {

    @Operation(summary = "Add a new privilege", description = "Creates a new privilege")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Privilege created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<PrivilegeDto> addPrivilege(@Valid @RequestBody @Schema(implementation = PrivilegePayload.class) PrivilegePayload command);

    @Operation(summary = "Edit an existing privilege", description = "Updates an existing privilege")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Privilege updated successfully"),
        @ApiResponse(responseCode = "404", description = "Privilege not found")
    })
    ResponseEntity<Privilege> editPrivilege(@Parameter(description = "The ID of the privilege to be updated", required = true)
                                            @PathVariable Long privilegeId,
                                            @Valid @RequestBody @Schema(implementation = PrivilegePayload.class) PrivilegePayload command);

    @Operation(summary = "Get all privileges", description = "Retrieves all privileges")
    @ApiResponse(responseCode = "200", description = "Privileges retrieved successfully")
    ResponseEntity<List<PrivilegeDto>> getAllPrivileges();

    @Operation(summary = "Get privilege by ID", description = "Retrieves a privilege by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Privilege retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Privilege not found")
    })
    ResponseEntity<PrivilegeDto> getPrivilegeById(@Parameter(description = "The ID of the privilege to be retrieved", required = true)
                                                  @PathVariable Long privilegeId);
}