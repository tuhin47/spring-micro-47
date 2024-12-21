package me.tuhin47.auth.command.handler;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.common.RoleDto;
import me.tuhin47.auth.payload.mapper.RoleMapper;
import me.tuhin47.auth.repo.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleCommandHandler {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    public Role addRole(RoleDto roleDto) {
        return roleRepository.save(roleMapper.toEntity(roleDto));
    }

    @Transactional
    public Role editRole(Long roleId, RoleDto roleDto) {
        Role role = roleRepository.getReferenceById(roleId);
        return roleRepository.save(roleMapper.partialUpdate(roleDto, role));
    }
}