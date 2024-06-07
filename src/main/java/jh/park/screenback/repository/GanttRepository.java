package jh.park.screenback.repository;

import jh.park.screenback.model.Gantt;
import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GanttRepository extends JpaRepository<Gantt, Long> {
    List<Gantt> findAllByCreatedUser(User user);

    List<Gantt> findAllByUserGroupId(UserGroup userGroup);
}