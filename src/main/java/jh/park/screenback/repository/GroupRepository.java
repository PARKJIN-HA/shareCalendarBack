package jh.park.screenback.repository;

import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<UserGroup, Long> {
    List<UserGroup> findAllByOwner(User owner);

    List<UserGroup> findUserGroupsByGroupMembers_Id(Long groupMemberId);
}
