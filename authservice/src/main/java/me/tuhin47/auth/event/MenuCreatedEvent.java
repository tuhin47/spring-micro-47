package me.tuhin47.auth.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuCreatedEvent {
    private Long menuId;
    private String label;
    private String icon;
    private Long parentId;
}