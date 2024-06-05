package jh.park.screenback.model;

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
    @JsonManagedReference
    private UserGroup group;

    @OneToMany(mappedBy = "id")
    @JsonManagedReference
    private List<User> users;

    @ManyToOne
    @JsonManagedReference
    private File files;
}