package jh.park.screenback.controller;

import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import jh.park.screenback.service.GroupService;
import jh.park.screenback.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import jh.park.screenback.model.Gantt;
import jh.park.screenback.service.GanttService;

import java.util.List;

@RestController
@RequestMapping("/api/gantt")
public class GanttController {

    @Autowired
    private GanttService ganttService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Gantt>> getGanttTasks(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        List<UserGroup> groups = groupService.findAllByOwner(user);
        List<Gantt> ganttSchedules = ganttService.findAllByCreatedUser(user);
        ganttSchedules.addAll(ganttService.findAllByGroupIDs(groups));

        return ResponseEntity.ok(ganttSchedules);
    }

    @PostMapping
    public ResponseEntity<Gantt> insertGanttSchedule(@RequestBody Gantt gantt, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(401).build(); // Unauthorized if user is null
        }
        gantt.setCreatedUser(user);
        gantt.setUserGroupId(groupService.findAllByOwner(user).get(0));
        Gantt createdGantt = ganttService.save(gantt);
        return ResponseEntity.ok(createdGantt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gantt> updateGanttSchedule(@PathVariable Long id, @RequestBody Gantt ganttDetails) {
        // Implement update logic here
        Gantt updatedGantt = ganttService.update(id, ganttDetails);
        System.out.println("Updated Gantt Task: " + updatedGantt.getTitle());
        return ResponseEntity.ok(updatedGantt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGanttSchedule(@PathVariable Long id) {
        ganttService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
