package me.tuhin47.auth.repo;

import me.tuhin47.auth.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}