package me.tuhin47.auth.repo;

import me.tuhin47.auth.model.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    @EntityGraph("Role.withUsersPrivileges")
    Role getReferenceById(Long id);

    @Override
    @EntityGraph("Role.withUsersPrivileges")
    List<Role> findAll();

}