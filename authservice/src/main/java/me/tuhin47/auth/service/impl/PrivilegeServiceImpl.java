// PrivilegeQueryHandler.java
package me.tuhin47.auth.service.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.payload.mapper.PrivilegeMapper;
import me.tuhin47.auth.payload.response.PrivilegeDto;
import me.tuhin47.auth.repo.PrivilegeRepository;
import me.tuhin47.auth.service.PrivilegeService;
import me.tuhin47.config.exception.common.AuthServiceExceptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeMapper privilegeMapper;

    @Override
    public List<PrivilegeDto> getAllPrivileges() {
        return privilegeRepository.findAll().stream().map(privilegeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Privilege getPrivilegeById(Long privilegeId) {
        return privilegeRepository.findById(privilegeId)
                                  .orElseThrow(AuthServiceExceptions.PRIVILEGE_NOT_FOUND.apply(privilegeId));
    }

}