package me.tuhin47.auth.payload.common;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link me.tuhin47.auth.model.Menu}
 */
@Value
public class MenuDto implements Serializable {
    Long id;
    String label;
    Long parentId;
    String icon;
    Set<Long> privilegeIds;
    Set<MenuDto> children;
}