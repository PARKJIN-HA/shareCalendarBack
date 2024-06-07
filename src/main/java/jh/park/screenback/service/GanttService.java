package jh.park.screenback.service;

import jh.park.screenback.model.User;
import jh.park.screenback.model.UserGroup;
import jh.park.screenback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jh.park.screenback.model.Gantt;
import jh.park.screenback.repository.GanttRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GanttService {

    @Autowired
    private GanttRepository ganttRepository;
    @Autowired
    private UserRepository userRepository;

    public Gantt save(Gantt gantt) {
        return ganttRepository.save(gantt);
    }

    public Gantt update(Long id, Gantt ganttDetails) {
        Optional<Gantt> optionalGantt = ganttRepository.findById(id);
        if (optionalGantt.isPresent()) {
            Gantt gantt = optionalGantt.get();
            gantt.setTitle(ganttDetails.getTitle());
            return ganttRepository.save(gantt);
        } else {
            throw new RuntimeException("Gantt not found with id " + id);
        }
    }

    public void deleteById(Long id) {
        ganttRepository.deleteById(id);
    }

    public Optional<Gantt> findById(Long id) {
        return ganttRepository.findById(id);
    }

    public List<Gantt> findAllByCreatedUser(User user) {
        return ganttRepository.findAllByCreatedUser(user);
    }

    public List<Gantt> findAllByGroupID(UserGroup userGroup) {
        return ganttRepository.findAllByUserGroupId(userGroup);
    }

    public List<Gantt> findAllByGroupIDs(List<UserGroup> groups) {
        List<Gantt> ganttSchedules = new ArrayList<>();
        for (UserGroup group : groups) {
            ganttSchedules.addAll(ganttRepository.findAllByUserGroupId(group));
        }
        return ganttSchedules;
    }

    // Add other necessary methods
}
