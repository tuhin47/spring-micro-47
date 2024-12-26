package me.tuhin47.auth.payload.mapper;

import me.tuhin47.auth.command.MenuPayload;
import me.tuhin47.auth.model.Menu;
import me.tuhin47.auth.payload.response.MenuDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuMapper {

    @Mapping(source = "parent.id", target = "parentId")
    MenuDto toDto(Menu menu);

    Menu toEntity(MenuPayload menuPayload);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Menu partialUpdate(MenuPayload menuPayload, @MappingTarget Menu menu);
}