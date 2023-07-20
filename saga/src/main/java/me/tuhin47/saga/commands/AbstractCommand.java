package me.tuhin47.saga.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class AbstractCommand<T> {

    @TargetAggregateIdentifier
    T id;
}