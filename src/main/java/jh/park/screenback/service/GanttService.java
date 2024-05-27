package jh.park.screenback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jh.park.screenback.model.Gantt;
import jh.park.screenback.repository.GanttRepository;

import java.util.Optional;

@Service
public class GanttService {

    @Autowired
    private GanttRepository ganttRepository;

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

    // Add other necessary methods
}
