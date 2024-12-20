package com.vijay.vz.controller;

import com.vijay.vz.entity.Task;
import com.vijay.vz.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> taskList = taskService.getAllTasks();
        return ResponseEntity.ok(taskList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks(){
        return ResponseEntity.ok(taskService.getPendingTasks());
    }


    @GetMapping("/getTask/{id}")
    public ResponseEntity<Task> getPendingTasks(@PathVariable String id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
}

