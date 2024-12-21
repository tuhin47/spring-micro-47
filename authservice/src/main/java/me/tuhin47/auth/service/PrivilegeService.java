package me.tuhin47.auth.service;

import me.tuhin47.auth.model.Privilege;

import java.util.List;

public interface PrivilegeService {
    List<Privilege> getAllPrivileges();
    Privilege getPrivilegeById(Long privilegeId);
}
