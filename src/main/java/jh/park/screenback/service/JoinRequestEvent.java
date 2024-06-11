package jh.park.screenback.service;

import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class JoinRequestEvent extends ApplicationEvent {
    private final UserGroup group;
    private final User user;

    public JoinRequestEvent(Object source, UserGroup group, User user) {
        super(source);
        this.group = group;
        this.user = user;
    }
}
