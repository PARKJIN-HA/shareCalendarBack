package jh.park.screenback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jh.park.screenback.model.UserGroup;
import jh.park.screenback.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<UserGroup> createGroup(@RequestBody UserGroup userGroup) {
        UserGroup createdUserGroup = groupService.save(userGroup);
        return ResponseEntity.ok(createdUserGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
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
