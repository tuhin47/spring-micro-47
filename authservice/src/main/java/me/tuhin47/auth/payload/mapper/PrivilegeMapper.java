package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.command.PrivilegePayload;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.payload.response.PrivilegeDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface PrivilegeMapper {
    PrivilegeDto toDto(Privilege privilege);


    Privilege toEntity(PrivilegePayload privilegePayload);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Privilege partialUpdate(PrivilegePayload privilegePayload, @MappingTarget Privilege privilege);
}