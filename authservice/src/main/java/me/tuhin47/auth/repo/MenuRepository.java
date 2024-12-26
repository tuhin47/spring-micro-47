package me.tuhin47.auth.repo;

import me.tuhin47.auth.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MenuRepository extends CrudRepository<Menu, Long> {

    // TODO - Entity Graph
    @Query("select distinct d from Menu d left join fetch d.children  where d.id = ?1")
    Optional<Menu> getMenuFromParent(Long id);
}