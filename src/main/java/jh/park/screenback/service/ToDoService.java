package jh.park.screenback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jh.park.screenback.model.ToDo;
import jh.park.screenback.repository.ToDoRepository;

import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    public ToDo save(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo update(Long id, ToDo toDoDetails) {
        Optional<ToDo> optionalToDo = toDoRepository.findById(id);
        if (optionalToDo.isPresent()) {
            ToDo toDo = optionalToDo.get();
            toDo.setName(toDoDetails.getName());
            toDo.setCompleted(toDoDetails.isCompleted());
            // Update other fields as necessary
            return toDoRepository.save(toDo);
        } else {
            throw new RuntimeException("ToDo not found with id " + id);
        }
    }

    public void deleteById(Long id) {
        toDoRepository.deleteById(id);
    }

    public Optional<ToDo> findById(Long id) {
        return toDoRepository.findById(id);
    }

    // Add other necessary methods
}
