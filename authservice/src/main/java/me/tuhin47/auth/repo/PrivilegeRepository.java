package me.tuhin47.auth.repo;

import me.tuhin47.auth.model.Privilege;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    @Override
    @EntityGraph("Privilege.withUsersRoles")
    List<Privilege> findAll();
}