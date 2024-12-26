package me.tuhin47.auth.command.handler;

import lombok.AllArgsConstructor;
import me.tuhin47.auth.command.PrivilegePayload;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.payload.mapper.PrivilegeMapper;
import me.tuhin47.auth.repo.PrivilegeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PrivilegeCommandHandler {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    @Transactional
    public Privilege addPrivilege(PrivilegePayload command) {
        Privilege entity = privilegeMapper.toEntity(command);
        return privilegeRepository.save(entity);
    }

    @Transactional
    public Privilege editPrivilege(Long privilegeId, PrivilegePayload command) {
        Privilege target = privilegeRepository.getReferenceById(privilegeId);
        return privilegeRepository.save(privilegeMapper.partialUpdate(command, target));
    }
}