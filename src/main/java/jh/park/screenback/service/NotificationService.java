package jh.park.screenback.service;

import jakarta.transaction.Transactional;
import jh.park.screenback.dto.NotificationDTO;
import jh.park.screenback.model.Notification;
import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import jh.park.screenback.repository.NotificationRepository;
import jh.park.screenback.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
public class NotificationService {
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final GroupService groupService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, GroupService groupService) {
        this.notificationRepository = notificationRepository;
        this.groupService = groupService;
    }

    public List<Notification> getReadNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return notificationRepository.findByUserAndIsReadTrue(user);
    }

    public Page<Notification> getUnreadNotifications(Long userId, PageRequest pageRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return notificationRepository.findByUserAndIsReadFalse(user, pageRequest);
    }

    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }


    @EventListener
    public void handleJoinRequestEvent(JoinRequestEvent event) {
        Notification notification = new Notification();
        notification.setMessage("You have a join request for group: " + event.getGroup().getName());
        notification.setUser(event.getUser());
        notification.setUserGroup(event.getGroup());
        notification.setType(99);
        notification.setRead(false);
        save(notification);
    }

    @EventListener
    public void handleJoinRequestAcceptedEvent(JoinRequestAcceptedEvent event) {
        Notification notification = new Notification();
        notification.setMessage("Your join request for group: " + event.getGroup().getName() + " has been accepted");
        notification.setUser(event.getUser());
        notification.setUserGroup(event.getGroup());
        notification.setType(98);
        notification.setRead(false);
        save(notification);
    }

    public void addNotification(Notification notification) {
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> findAllByOwner(User owner) {
        return notificationRepository.findAllByUser(owner);
    }

    public List<Notification> findUserGroupsByGroupMember(User groupMember) {
        List<UserGroup> userGroups = groupService.findUserGroupsByGroupMember(groupMember);
        List<Notification> notifications = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            notifications.addAll(notificationRepository.findAllByUserGroup(userGroup));
        }
        return notifications;
    }

    public Notification update(Long id, NotificationDTO notification) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification existingNotification = optionalNotification.get();

            return notificationRepository.save(existingNotification);
        }
        return null;
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }
}
