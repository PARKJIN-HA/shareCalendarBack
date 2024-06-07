package jh.park.screenback.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Gantt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;

    private int progress;
    private LocalDateTime editTime;

    @ManyToOne
    private User editUser;

    @ManyToOne
    private User createdUser;

    @ManyToOne
    private UserGroup userGroupId;

    @ManyToOne
    private File file;
}
