package jh.park.screenback.repository;

import jh.park.screenback.model.Notification;
import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findAllByUser(User owner);
    public List<Notification> findAllByUserGroup(UserGroup userGroup);

    List<Notification> findByUserAndIsReadTrue(User user);
    Page<Notification> findByUserAndIsReadFalse(User user, Pageable pageable);
    Optional<Notification> findByIdAndUserId(Long id, Long userId);
}