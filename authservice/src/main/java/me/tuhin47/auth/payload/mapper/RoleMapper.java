package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.command.RolePayload;
import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.response.RoleDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RolePayload payload);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role partialUpdate(RolePayload payload, @MappingTarget Role role);
}