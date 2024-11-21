package com.vijay.vz.controller;

import com.vijay.vz.entity.Task;
import com.vijay.vz.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks/advanced")
public class AdvancedTaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/pending/sorted")
    public List<Task> getPendingTasksSortedByDueDate() {
        return taskService.getPendingTasksSortedByDueDate();
    }

    @GetMapping("/completed/titles")
    public List<String> getCompletedTaskTitles() {
        return taskService.getCompletedTaskTitles();
    }

    @GetMapping("/average/priority")
    public double getAverageTaskPriority() {
        return taskService.getAverageTaskPriority();
    }

    @GetMapping("/high-priority/process")
    public List<Task> processHighPriorityTasks() {
        return taskService.processHighPriorityTasks();
    }

    @GetMapping("/summary")
    public String getTaskSummaryByStatus() {
        return taskService.getTaskSummaryByStatus();
    }
}
