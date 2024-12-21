package me.tuhin47.entity.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.config.redis.RedisUserService;

import java.io.IOException;
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
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        var userRoles = Objects.requireNonNull(RedisUserService.getCurrentUser()).getAuthorityNames();

        if (!allowedRoles.isEmpty() && allowedRoles.stream().noneMatch(userRoles::contains)) {
            gen.writeString("");
        } else if (provider instanceof DefaultSerializerProvider defaultSerializerProvider) {
            defaultSerializerProvider.serializeValue(gen, value);
        } else {
            gen.writeString(value.toString());
        }
    }


}

// TODO : Similar implementation for SensitiveWriteDeserializer