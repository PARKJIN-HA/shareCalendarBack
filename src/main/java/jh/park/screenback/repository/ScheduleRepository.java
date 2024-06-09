package jh.park.screenback.repository;

import jh.park.screenback.model.Schedule;
import jh.park.screenback.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findSchedulesByGroup_Id(Long groupId);
}