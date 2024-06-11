package jh.park.screenback.controller;

import jh.park.screenback.dto.UserGroupDTO;
import jh.park.screenback.model.Gantt;
import jh.park.screenback.model.User;
import jh.park.screenback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jh.park.screenback.model.UserGroup;
import jh.park.screenback.service.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<UserGroup>> getGroups(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.findById(userId);
        List<UserGroup> userGroup = groupService.findAllByOwner(user);
        for (Object o : groupService.findAllByGroupMember(user)) {
            if (o instanceof UserGroup) {
                if (!userGroup.contains(o)) {
                    userGroup.add((UserGroup) o);
                }
            }
        }
        return ResponseEntity.ok(userGroup);
    }

    @PostMapping
    public ResponseEntity<UserGroup> createGroup(@RequestBody UserGroup userGroup, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.findById(userId);
        userGroup.setOwner(user);

        List<User> groupMembers = new ArrayList<>();
        groupMembers.add(user);
        userGroup.setGroupMembers(groupMembers);

        UserGroup createdUserGroup = groupService.save(userGroup);
        System.out.println("Created UserGroup: " + createdUserGroup.toString());
        return ResponseEntity.ok(createdUserGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<UserGroup> updateGroup(@PathVariable Long groupId, @RequestBody UserGroupDTO userGroup) {
        System.out.println("Updating group" + groupId + " with " + userGroup.toString());
        UserGroup updatedUserGroup = groupService.update(groupId, userGroup);
        return ResponseEntity.ok(updatedUserGroup);
    }

    @PostMapping("/{groupId}/join-request")
    public ResponseEntity<Void> sendJoinRequest(@PathVariable Long groupId, @RequestBody String userId) {
        Optional<UserGroup> group = groupService.findById(groupId);
        User user = userService.findByEmail(userId);
        if (group.isPresent() && user != null) {
            groupService.sendJoinRequestNotification(group.get(), user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{groupId}/accept-request")
    public ResponseEntity<UserGroup> acceptJoinRequest(@PathVariable Long groupId, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        UserGroup updatedGroup = groupService.acceptJoinRequest(groupId, userId);
        if (updatedGroup != null) {
            return ResponseEntity.ok(updatedGroup);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<String> joinGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        // Implement join group logic here
        return ResponseEntity.ok("User joined group successfully");
    }

    @PostMapping("/{groupId}/quit")
    public ResponseEntity<String> quitGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        // Implement quit group logic here
        return ResponseEntity.ok("User quit group successfully");
    }
}
