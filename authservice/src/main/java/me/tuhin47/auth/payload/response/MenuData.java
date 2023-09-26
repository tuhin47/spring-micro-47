package me.tuhin47.auth.payload.response;

import me.tuhin47.auth.model.Menu;

import java.util.Set;

/**
 * Projection for {@link Menu}
 */
public interface MenuData {
    Long getId();

    String getLabel();

    String getIcon();

    Set<MenuData> getChildren();
}