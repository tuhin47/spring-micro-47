package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.model.Role;
import me.tuhin47.auth.payload.common.RoleDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface RoleMapper {


    @Mapping(source = "id", target = "roleId")
    Role toEntity(RoleDto roleDto);

    @Mapping(source = "roleId", target = "id")
    RoleDto toDto(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "roleId")
    Role partialUpdate(RoleDto roleDto, @MappingTarget Role role);
}