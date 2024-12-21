package me.tuhin47.auth.controller.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.command.handler.RoleCommandHandler;
import me.tuhin47.auth.controller.RoleController;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.common.RoleDto;
import me.tuhin47.auth.payload.mapper.RoleMapper;
import me.tuhin47.auth.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Role> addRole(@RequestBody RoleDto roleDto) {
        Role role = roleCommandHandler.addRole(roleDto);
        return ResponseEntity.ok(role);
    }

    @Override
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> editRole(@PathVariable Long roleId, @RequestBody @Valid RoleDto roleDto) {
        return ResponseEntity.ok(roleMapper.toDto(roleCommandHandler.editRole(roleId, roleDto)));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Override
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) {
        Role role = roleService.getRoleById(roleId);
        return ResponseEntity.ok(role);
    }
}