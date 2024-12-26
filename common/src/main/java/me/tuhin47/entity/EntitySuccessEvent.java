package me.tuhin47.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class EntitySuccessEvent extends ApplicationEvent {
    private final EntityStatus status;
    private final BaseEntity<?> entity;


    public EntitySuccessEvent(BaseEntity<?> source, EntityStatus status) {
        super(source);
        this.status = status;
        this.entity = source;
    }
}
