package me.tuhin47.auth.controller.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.command.PrivilegePayload;
import me.tuhin47.auth.command.handler.PrivilegeCommandHandler;
import me.tuhin47.auth.controller.PrivilegeController;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.payload.mapper.PrivilegeMapper;
import me.tuhin47.auth.payload.response.PrivilegeDto;
import me.tuhin47.auth.service.PrivilegeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/privileges")
@RequiredArgsConstructor
public class PrivilegeControllerImpl implements PrivilegeController {

    private final PrivilegeCommandHandler privilegeCommandHandler;
    private final PrivilegeService privilegeService;
    private final PrivilegeMapper privilegeMapper;

    @PostMapping
    @Override
    public ResponseEntity<PrivilegeDto> addPrivilege(@RequestBody @Valid PrivilegePayload command) {
        Privilege privilege = privilegeCommandHandler.addPrivilege(command);
        return new ResponseEntity<>(privilegeMapper.toDto(privilege), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{privilegeId}")
    public ResponseEntity<Privilege> editPrivilege(@PathVariable Long privilegeId, @RequestBody PrivilegePayload command) {
        Privilege privilege = privilegeCommandHandler.editPrivilege(privilegeId, command);
        return ResponseEntity.ok(privilege);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PrivilegeDto>> getAllPrivileges() {
        return ResponseEntity.ok(privilegeService.getAllPrivileges());
    }

    @Override
    @GetMapping("/{privilegeId}")
    public ResponseEntity<PrivilegeDto> getPrivilegeById(@PathVariable Long privilegeId) {
        Privilege privilege = privilegeService.getPrivilegeById(privilegeId);
        return ResponseEntity.ok(privilegeMapper.toDto(privilege));
    }
}