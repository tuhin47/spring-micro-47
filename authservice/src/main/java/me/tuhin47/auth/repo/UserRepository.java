package me.tuhin47.auth.repo;

import me.tuhin47.auth.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph("User.withRolesPrivileges")
    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Override
    @EntityGraph("User.withRolesPrivileges")
    User getReferenceById(String id);

}
