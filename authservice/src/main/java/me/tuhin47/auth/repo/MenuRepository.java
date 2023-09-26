package me.tuhin47.auth.repo;

import me.tuhin47.auth.model.Menu;
import me.tuhin47.auth.payload.response.MenuData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MenuRepository extends CrudRepository<Menu, Long> {

    @Query("select d from Menu d join fetch d.children  where d.id = ?1")
    Optional<MenuData> getAllMenuDataFromRoot(Long id);
}