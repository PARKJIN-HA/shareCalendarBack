package jh.park.screenback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime lastEditTime;
    private String location;

//    @ManyToOne
//    private User editFirstUser;
//
//    @ManyToOne
//    private User editLastUser;

    @ManyToOne
    @JsonBackReference(value = "user-group-schedules")
    private UserGroup group;

    @ManyToOne
    @JsonBackReference(value = "user-owner")
    private User owner;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "schedule-files")
    private List<File> files;
}