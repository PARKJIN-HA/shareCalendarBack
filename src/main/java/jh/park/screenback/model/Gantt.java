package jh.park.screenback.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
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

    public Long getEditUserId() {
        return this.editUser != null ? this.editUser.getId() : null;
    }

    public Long getCreatedUserId() {
        return this.createdUser != null ? this.createdUser.getId() : null;
    }

    public Long getUserGroupId() {
        return this.userGroupId != null ? this.userGroupId.getId() : null;
    }

    public Long getFileId() {
        return this.file != null ? this.file.getId() : null;
    }
}
