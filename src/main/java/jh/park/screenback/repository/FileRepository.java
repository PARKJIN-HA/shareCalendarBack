package jh.park.screenback.repository;

import jh.park.screenback.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}