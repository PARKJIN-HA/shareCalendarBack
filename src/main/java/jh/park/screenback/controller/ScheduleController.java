package jh.park.screenback.controller;

import jakarta.servlet.http.HttpServletRequest;
import jh.park.screenback.dto.ScheduleDTO;
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

        List<UserGroup> userGroups = groupService.findUserGroupsByGroupMember(user);
        System.out.println("User groups: " + userGroups);

        List<Schedule> schedules = new ArrayList<>();

        for (UserGroup group : userGroups) {
            List<Schedule> groupSchedules = scheduleService.findByUserGroup(group);
            if (groupSchedules != null && !groupSchedules.isEmpty()) {
                schedules.addAll(groupSchedules);
                System.out.println("Schedules found for group " + group.getName() + ": " + groupSchedules);
            } else {
                System.out.println("No schedules found for group " + group.getName());
            }
        }

        if (schedules.isEmpty()) {
            System.out.println("No schedules found for user " + user.getUsername());
        }

        return ResponseEntity.ok(schedules);
    }

    @PostMapping
    public ResponseEntity<Schedule> insertSchedule(@RequestBody ScheduleDTO scheduleDTO, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());

        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleDTO.getTitle());
        schedule.setStart(scheduleDTO.getStart());
        schedule.setEnd(scheduleDTO.getEnd());
        schedule.setLocation(scheduleDTO.getLocation());
        schedule.setLastEditTime(LocalDateTime.now());
        schedule.setOwner(userService.findById(userId));
        groupService.findById(scheduleDTO.getGroupId()).ifPresent(schedule::setGroup);

        Schedule createdSchedule = scheduleService.save(schedule);
        System.out.println(createdSchedule.toString());
        return ResponseEntity.ok(createdSchedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule scheduleDetails) {
        // Implement update logic here
        System.out.println(scheduleDetails.toString());
        Schedule updatedSchedule = scheduleService.update(id, scheduleDetails);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
