package me.tuhin47.rule;

public interface Rule<T> {
    boolean evaluate(T obj);
    void execute(T obj);
}
