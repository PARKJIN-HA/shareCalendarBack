package jh.park.screenback.repository;

import jh.park.screenback.model.Gantt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GanttRepository extends JpaRepository<Gantt, Long> {
}