package jh.park.screenback.repository;

import jh.park.screenback.model.ToDoDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoDetailRepository extends JpaRepository<ToDoDetail, Long> {
}