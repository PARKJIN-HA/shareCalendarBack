package jh.park.screenback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime editLastTime;

    @ManyToOne
    private User editFirstUser;

    @ManyToOne
    private User editLastUser;

    private String location;

    @ManyToOne
    private Gantt gantt;

    @OneToMany(mappedBy = "schedule")
    private List<ToDo> toDos;
}