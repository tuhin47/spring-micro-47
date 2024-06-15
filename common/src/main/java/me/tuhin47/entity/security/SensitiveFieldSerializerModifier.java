package me.tuhin47.entity.security;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;

public class SensitiveFieldSerializerModifier extends BeanSerializerModifier {
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
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