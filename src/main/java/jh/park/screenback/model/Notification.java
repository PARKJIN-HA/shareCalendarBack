package jh.park.screenback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private boolean isRead;
    private int type;

    private String url;

    @ManyToOne
    private User user;

    @ManyToOne
    private UserGroup userGroup;
}
