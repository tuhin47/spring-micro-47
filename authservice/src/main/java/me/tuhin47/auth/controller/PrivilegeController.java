package me.tuhin47.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.tuhin47.auth.command.PrivilegePayload;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.core.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Privilege API", tags = "PRIVILEGE-API", description = "Operations related to privileges")
public interface PrivilegeController extends BaseController {

    @ApiOperation(value = "Add a new privilege", notes = "Creates a new privilege")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Privilege created successfully"),
        @ApiResponse(code = 400, message = "Invalid input")
    })
    ResponseEntity<Privilege> addPrivilege(@Valid @RequestBody PrivilegePayload command);

    @ApiOperation(value = "Edit an existing privilege", notes = "Updates an existing privilege")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Privilege updated successfully"),
        @ApiResponse(code = 404, message = "Privilege not found")
    })
    ResponseEntity<Privilege> editPrivilege(@PathVariable Long privilegeId, @Valid @RequestBody PrivilegePayload command);

    @ApiOperation(value = "Get all privileges", notes = "Retrieves all privileges")
    @ApiResponse(code = 200, message = "Privileges retrieved successfully")
    ResponseEntity<List<Privilege>> getAllPrivileges();

    @ApiOperation(value = "Get privilege by ID", notes = "Retrieves a privilege by its ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Privilege retrieved successfully"),
        @ApiResponse(code = 404, message = "Privilege not found")
    })
    ResponseEntity<Privilege> getPrivilegeById(@PathVariable Long privilegeId);
}
