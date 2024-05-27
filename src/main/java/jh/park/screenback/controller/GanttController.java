package jh.park.screenback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jh.park.screenback.model.Gantt;
import jh.park.screenback.service.GanttService;

@RestController
@RequestMapping("/api/gantt")
public class GanttController {

    @Autowired
    private GanttService ganttService;

//    @GetMapping
//    public ResponseEntity<Iterable<Gantt>> getGanttSchedules() {
//        Iterable<Gantt> ganttSchedules = ganttService.findAll();
//        return ResponseEntity.ok(ganttSchedules);
//    }

    @PostMapping
    public ResponseEntity<Gantt> insertGanttSchedule(@RequestBody Gantt gantt) {
        Gantt createdGantt = ganttService.save(gantt);
        return ResponseEntity.ok(createdGantt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gantt> updateGanttSchedule(@PathVariable Long id, @RequestBody Gantt ganttDetails) {
        // Implement update logic here
        Gantt updatedGantt = ganttService.update(id, ganttDetails);
        return ResponseEntity.ok(updatedGantt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGanttSchedule(@PathVariable Long id) {
        ganttService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
