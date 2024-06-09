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

    @ManyToOne
    private UserGroup group;

    @ManyToOne
    private User owner;

    @OneToMany
    private List<File> files;

    public Long getGroupId() {
        return this.group != null ? this.group.getId() : null;
    }

    public Long getOwnerId() {
        return this.owner != null ? this.owner.getId() : null;
    }

    public List<Long> getFiles() {
        return this.files != null ? this.files.stream().map(File::getId).toList() : null;
    }
}