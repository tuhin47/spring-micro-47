package me.tuhin47.auth.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuDeletedEvent {
    private Long menuId;
}