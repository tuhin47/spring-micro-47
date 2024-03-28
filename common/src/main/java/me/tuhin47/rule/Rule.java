package me.tuhin47.rule;

public interface Rule<T> {
    boolean evaluate(T t);
    void execute(T t);
}
