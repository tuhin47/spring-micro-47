package me.tuhin47.entity.security;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.ValueSerializerModifier;

import java.util.List;

public class SensitiveFieldSerializerModifier extends ValueSerializerModifier {
    @Override
    public List<BeanPropertyWriter> changeProperties(
        SerializationConfig config, BeanDescription.Supplier beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter writer : beanProperties) {
            SensitiveRead sensitiveRead = writer.getAnnotation(SensitiveRead.class);
            if (sensitiveRead != null) {
                writer.assignSerializer(new SensitiveReadSerializer(writer, sensitiveRead.rolesAllowed()));
            }
            // TODO : Similar logic for deserialization
        }
        return beanProperties;
    }
}