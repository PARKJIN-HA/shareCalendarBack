package jh.park.screenback.controller;

import jakarta.servlet.http.HttpServletRequest;
import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import jh.park.screenback.service.GroupService;
import jh.park.screenback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jh.park.screenback.model.Schedule;
import jh.park.screenback.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        User user = userService.findById(userId);
        List<UserGroup> userGroups = groupService.findAllByGroupMember(user);
        List<Schedule> schedules = new ArrayList<>();

        for (UserGroup group : userGroups) {
            List<Schedule> groupSchedules = scheduleService.findByUserGroup(group);
            if (groupSchedules != null) {
                schedules.addAll(groupSchedules);
            }
        }

        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    public ResponseEntity<Schedule> insertSchedule(@RequestBody Schedule schedule, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        System.out.println(schedule);
//        schedule.setEditFirstUser(userService.findById(userId));
//        schedule.setEditLastUser(userService.findById(userId));
        schedule.setLastEditTime(LocalDateTime.now());
        Schedule createdSchedule = scheduleService.save(schedule);
        return ResponseEntity.ok(createdSchedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule scheduleDetails) {
        // Implement update logic here
        Schedule updatedSchedule = scheduleService.update(id, scheduleDetails);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
