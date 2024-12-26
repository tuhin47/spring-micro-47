package me.tuhin47.auth.service;

import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.payload.response.PrivilegeDto;

import java.util.List;

public interface PrivilegeService {
    List<PrivilegeDto> getAllPrivileges();

    Privilege getPrivilegeById(Long privilegeId);
}
