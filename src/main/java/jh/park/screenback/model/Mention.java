package jh.park.screenback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Mention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private UserGroup userGroup;

    @ManyToOne
    private ToDo toDo;

    @ManyToOne
    private Gantt gantt;
}
