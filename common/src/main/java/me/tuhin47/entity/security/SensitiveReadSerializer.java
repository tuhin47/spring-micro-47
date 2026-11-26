package me.tuhin47.entity.security;

import lombok.extern.slf4j.Slf4j;
import me.tuhin47.config.redis.RedisUserService;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.std.StdSerializer;

import java.util.Objects;
import java.util.Set;

@Slf4j
public class SensitiveReadSerializer extends StdSerializer<Object> {
    private final Set<String> allowedRoles;

    public SensitiveReadSerializer(BeanPropertyWriter writer, String[] rolesAllowed) {
        super(writer.getType());
        this.allowedRoles = Set.of(rolesAllowed);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializationContext provider) {
        var userRoles = Objects.requireNonNull(RedisUserService.getCurrentUser()).getAuthorityNames();

        if (!allowedRoles.isEmpty() && allowedRoles.stream().noneMatch(userRoles::contains)) {
            gen.writeString("");
        } else {
            provider.findValueSerializer(value.getClass()).serialize(value, gen, provider);
        }
    }
}

// TODO : Similar implementation for SensitiveWriteDeserializer