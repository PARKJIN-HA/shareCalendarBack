package jh.park.screenback.service;

import jh.park.screenback.model.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jh.park.screenback.model.Schedule;
import jh.park.screenback.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule update(Long id, Schedule scheduleDetails) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setTitle(scheduleDetails.getTitle());
            schedule.setStart(scheduleDetails.getStart());
            schedule.setEnd(scheduleDetails.getEnd());
            schedule.setLastEditTime(scheduleDetails.getLastEditTime());
//            schedule.setEditLastUser(scheduleDetails.getEditLastUser());
            schedule.setLocation(scheduleDetails.getLocation());
            return scheduleRepository.save(schedule);
        } else {
            throw new RuntimeException("Schedule not found with id " + id);
        }
    }

    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    public Iterable<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByUserGroup(UserGroup group) {
        return scheduleRepository.findSchedulesByGroup_Id(group.getId());
    }

    // Add other necessary methods
}
