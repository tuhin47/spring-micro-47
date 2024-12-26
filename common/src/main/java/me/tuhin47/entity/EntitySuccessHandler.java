package me.tuhin47.entity;

@FunctionalInterface
public interface EntitySuccessHandler {
    void executeTask(EntitySuccessEvent event);
}
