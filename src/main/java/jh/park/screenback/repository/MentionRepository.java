package jh.park.screenback.repository;

import jh.park.screenback.model.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Long> {
}