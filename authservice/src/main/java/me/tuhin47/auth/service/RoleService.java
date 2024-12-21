package me.tuhin47.auth.service;

import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.common.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAllRoles();
    Role getRoleById(Long roleId);
}
