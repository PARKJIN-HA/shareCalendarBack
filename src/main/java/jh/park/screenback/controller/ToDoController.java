package jh.park.screenback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jh.park.screenback.model.ToDo;
import jh.park.screenback.service.ToDoService;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @PostMapping
    public ResponseEntity<ToDo> insertToDo(@RequestBody ToDo toDo) {
        ToDo createdToDo = toDoService.save(toDo);
        return ResponseEntity.ok(createdToDo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDoDetails) {
        // Implement update logic here
        ToDo updatedToDo = toDoService.update(id, toDoDetails);
        return ResponseEntity.ok(updatedToDo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
        toDoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
