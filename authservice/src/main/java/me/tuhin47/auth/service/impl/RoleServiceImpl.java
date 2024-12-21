package me.tuhin47.auth.service.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.common.RoleDto;
import me.tuhin47.auth.payload.mapper.RoleMapper;
import me.tuhin47.auth.repo.RoleRepository;
import me.tuhin47.auth.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static me.tuhin47.config.exception.common.AuthServiceExceptions.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                             .orElseThrow(ROLE_NOT_FOUND.apply(roleId, null));
    }
}
