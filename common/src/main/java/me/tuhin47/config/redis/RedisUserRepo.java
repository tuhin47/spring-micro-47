package me.tuhin47.config.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserRepo extends CrudRepository<UserRedis, String> {
}
