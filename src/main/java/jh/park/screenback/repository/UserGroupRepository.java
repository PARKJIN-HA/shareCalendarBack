package jh.park.screenback.repository;

import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    List<UserGroup> findAllByOwner(User ownerId);
}
