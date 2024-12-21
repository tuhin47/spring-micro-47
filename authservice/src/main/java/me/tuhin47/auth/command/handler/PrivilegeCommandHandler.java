package me.tuhin47.auth.command.handler;

import lombok.AllArgsConstructor;
import me.tuhin47.auth.command.PrivilegePayload;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.repo.PrivilegeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static me.tuhin47.config.exception.common.AuthServiceExceptions.PRIVILEGE_NOT_FOUND;

@Service
@AllArgsConstructor
public class PrivilegeCommandHandler {

    private final PrivilegeRepository privilegeRepository;

    @Transactional
    public Privilege addPrivilege(PrivilegePayload command) {
        Privilege privilege = new Privilege();
        privilege.setName(command.name());
        privilege.setDescription(command.description());
        return privilegeRepository.save(privilege);
    }

    @Transactional
    public Privilege editPrivilege(Long privilegeId, PrivilegePayload command) {
        Privilege privilege = privilegeRepository.findById(privilegeId)
                                                 .orElseThrow(PRIVILEGE_NOT_FOUND.apply(privilegeId));
        privilege.setName(command.name());
        privilege.setDescription(command.description());
        return privilegeRepository.save(privilege);
    }
}