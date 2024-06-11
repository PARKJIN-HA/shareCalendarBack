package jh.park.screenback.controller;

import jh.park.screenback.model.Notification;
import jh.park.screenback.model.User;
import jh.park.screenback.service.NotificationService;
import jh.park.screenback.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getNoti(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.findById(userId);

        List<Notification> notifications = new ArrayList<>();
        notifications.addAll(notificationService.findAllByOwner(user));
        notifications.addAll(notificationService.findUserGroupsByGroupMember(user));

        if (notifications.isEmpty()) {
            System.out.println("No notifications");
            return ResponseEntity.noContent().build();
        }

        System.out.println("Notifications: " + notifications);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/list")
    public String getNotiList() {
        return "notiList";
    }


    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Notification notify(Notification notification) {
        return notification;
    }


    @GetMapping("/read")
    public ResponseEntity<List<Notification>> getReadNotifications(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        List<Notification> notifications = notificationService.getReadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/unread")
    public ResponseEntity<Page<Notification>> getUnreadNotifications(
            @RequestParam int page,
            @RequestParam int size,
            Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        Page<Notification> notifications = notificationService.getUnreadNotifications(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/mark-as-read/{id}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        notificationService.markAsRead(id, userId);
        return ResponseEntity.ok().build();
    }

}
