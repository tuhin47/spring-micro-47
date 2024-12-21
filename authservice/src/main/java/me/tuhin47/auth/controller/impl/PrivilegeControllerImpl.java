package me.tuhin47.auth.controller.impl;

import lombok.RequiredArgsConstructor;
import me.tuhin47.auth.command.PrivilegePayload;
import me.tuhin47.auth.command.handler.PrivilegeCommandHandler;
import me.tuhin47.auth.controller.PrivilegeController;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.service.PrivilegeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/privileges")
@RequiredArgsConstructor
public class PrivilegeControllerImpl implements PrivilegeController {

    private final PrivilegeCommandHandler privilegeCommandHandler;
    private final PrivilegeService privilegeService;


    @PostMapping
    public ResponseEntity<Privilege> addPrivilege(@RequestBody @Valid PrivilegePayload command) {
        Privilege privilege = privilegeCommandHandler.addPrivilege(command);
        return ResponseEntity.ok(privilege);
    }

    @PutMapping("/{privilegeId}")
    public ResponseEntity<Privilege> editPrivilege(@PathVariable Long privilegeId, @RequestBody PrivilegePayload command) {
        Privilege privilege = privilegeCommandHandler.editPrivilege(privilegeId, command);
        return ResponseEntity.ok(privilege);
    }

    @GetMapping
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        return ResponseEntity.ok(privileges);
    }

    @GetMapping("/{privilegeId}")
    public ResponseEntity<Privilege> getPrivilegeById(@PathVariable Long privilegeId) {
        Privilege privilege = privilegeService.getPrivilegeById(privilegeId);
        return ResponseEntity.ok(privilege);
    }
}