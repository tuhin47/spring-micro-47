package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.model.Menu;
import me.tuhin47.auth.model.Privilege;
import me.tuhin47.auth.payload.common.MenuDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuMapper {
    @Mapping(source = "parentId", target = "parent.id")
    Menu toEntity(MenuDto menuDto);

    @Mapping(target = "privilegeIds", expression = "java(privilegesToPrivilegeIds(menu.getPrivileges()))")
    @Mapping(source = "parent.id", target = "parentId")
    MenuDto toDto(Menu menu);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "parentId", target = "parent.id")
    Menu partialUpdate(MenuDto menuDto, @MappingTarget Menu menu);

    default Set<Long> privilegesToPrivilegeIds(Set<Privilege> privileges) {
        return privileges.stream().map(Privilege::getPrivilegeId).collect(Collectors.toSet());
    }
}