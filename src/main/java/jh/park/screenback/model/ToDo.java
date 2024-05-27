package jh.park.screenback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean completed;

    @ManyToOne
    private User user;

    @ManyToOne
    private Schedule schedule;

    @OneToMany(mappedBy = "toDo")
    private List<ToDoDetail> toDoDetails;

    @OneToMany(mappedBy = "toDo")
    private List<Mention> mentions;
}