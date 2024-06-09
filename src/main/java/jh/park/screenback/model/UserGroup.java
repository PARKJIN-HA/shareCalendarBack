package jh.park.screenback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<User> groupMembers;

    @OneToMany(mappedBy = "group")
    private List<Schedule> schedules;

    public Long getOwnerId() {
        return this.owner != null ? this.owner.getId() : null;
    }

    public List<Long> getGroupMembers() {
        return this.groupMembers != null ? this.groupMembers.stream().map(User::getId).toList() : null;
    }

    public List<Long> getSchedules() {
        return this.schedules != null ? this.schedules.stream().map(Schedule::getId).toList() : null;
    }
}
