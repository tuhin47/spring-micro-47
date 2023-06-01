package me.tuhin47.config;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserRepo extends CrudRepository<RedisUser,String> {
}
