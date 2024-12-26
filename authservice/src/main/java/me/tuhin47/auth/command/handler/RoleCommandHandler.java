package me.tuhin47.auth.command.handler;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.command.RolePayload;
import me.tuhin47.auth.model.Role;
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
    public Role addRole(RolePayload payload) {
        return roleRepository.save(roleMapper.toEntity(payload));
    }

    @Transactional
    public Role editRole(Long roleId, RolePayload payload) {
        Role role = roleRepository.getReferenceById(roleId);
        Role entity = roleMapper.partialUpdate(payload, role);
        return roleRepository.save(entity);
    }
}