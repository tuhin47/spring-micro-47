package me.tuhin47.auth.repo;

import me.tuhin47.auth.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph("User.withRolesPrivileges")
    User findByEmail(String email);

    @Override
    @EntityGraph("User.withRolesPrivileges")
    List<User> findAll();

    @Override
    @EntityGraph("User.withRolesPrivileges")
    List<User> findAllById(Iterable<String> ids);

    boolean existsByEmail(String email);

    @Override
    @EntityGraph("User.withRolesPrivileges")
    User getReferenceById(String id);

}
