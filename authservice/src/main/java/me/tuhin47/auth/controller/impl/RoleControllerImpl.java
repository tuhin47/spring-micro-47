package me.tuhin47.auth.controller.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.command.RolePayload;
import me.tuhin47.auth.command.handler.RoleCommandHandler;
import me.tuhin47.auth.controller.RoleController;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.mapper.RoleMapper;
import me.tuhin47.auth.payload.response.RoleDto;
import me.tuhin47.auth.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleCommandHandler roleCommandHandler;
    private final RoleService roleService;
    private final RoleMapper roleMapper;


    @Override
    @PostMapping
    public ResponseEntity<RoleDto> addRole(@RequestBody RolePayload payload) {
        Role role = roleCommandHandler.addRole(payload);
        return new ResponseEntity<>(roleMapper.toDto(role), HttpStatus.CREATED);
    }


    @Override
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> editRole(@PathVariable Long roleId, @RequestBody RolePayload payload) {
        return ResponseEntity.ok(roleMapper.toDto(roleCommandHandler.editRole(roleId, payload)));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Override
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long roleId) {
        Role role = roleService.getRoleById(roleId);
        return ResponseEntity.ok(roleMapper.toDto(role));
    }
}