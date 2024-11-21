package com.vijay.vz.service;

import com.vijay.vz.entity.Task;
import com.vijay.vz.exception.BadRequestException;
import com.vijay.vz.exception.ResourceNotFoundException;
import com.vijay.vz.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    TaskRepository taskRepository;

    private static  final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public Task createTask(Task task){
        logger.info("Creating new task:{} ",task.getTitle());
        if(task.getTitle()==null || task.getTitle().isBlank()){
            throw new BadRequestException("Task title can't be empty");
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(){
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    public Task updateTask(String id,Task updatedTask){
        logger.info("Update task : {} ",updatedTask.getTitle());
        Optional<Task> optionalTask= taskRepository.findById(id);

        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());

            return taskRepository.save(task);

        }
        logger.error("Task with ID: {} not found to update",id );
        throw new RuntimeException("Task not found");
    }

    public void deleteTask(String id){
        logger.warn("Deleting task with ID: {}", id);
        taskRepository.deleteById(id);
    }

    public List<Task> getPendingTasks(){
        logger.info("Fetching pending tasks");
        Optional<List<Task>> allTask= Optional.of(taskRepository.findAll());
        List<Task> pendingTasks = null;
        if(allTask.isPresent()){
            pendingTasks=allTask.get().stream().filter( task -> task.getStatus().equalsIgnoreCase("PENDING")).toList();
        }
      return pendingTasks;
    }

    public Task getTaskById(String id) {
        logger.debug("Fetching task with ID:{},", id);
        return taskRepository.findById(id).orElseThrow(() -> {
            logger.error("Task with ID {} not found", id);
            return new ResourceNotFoundException("Task with id - " + id + " not found");
        });
    }


    /*public Task getTaskById(String id) {
        Optional<Task> optionalTask= taskRepository.findById(id);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }else{
            throw new ResourceNotFoundException("Task with id - "+id+" not found");
        }
    }*/

    //Filtering and Sorting Tasks
    public List<Task> getPendingTasksSortedByDueDate() {
        return taskRepository.findAll().stream()
                .filter(task -> "PENDING".equalsIgnoreCase(task.getStatus()))
                .filter(task -> {
                    if (task.getDueDate() == null) {
                        logger.debug("Skipping task with null dueDate: {}", task);
                        return false;
                    }
                    return true;
                })
                .sorted(Comparator.comparing(Task::getDueDate))
                .toList();
    }


    //Extract only task titles of "Completed" tasks.
    public List<String> getCompletedTaskTitles(){
        return taskRepository.findAll().stream()
                .filter( task -> task.getStatus().equalsIgnoreCase("COMPLETED"))
                .map(Task::getTitle).toList();
    }

    //Group tasks by their status and count how many are in each category.
    public Map<String,Long> getTaskStatusCount(){
        return taskRepository.findAll().stream()
                .collect(Collectors.groupingBy(Task::getStatus,Collectors.counting()));
    }

    //Calculate average priority of all tasks.
    public double getAverageTaskPriority() {
        return taskRepository.findAll().stream()
                .mapToInt(Task::getPriority)
                .average()
                .orElse(0.0);
    }

    //Process high-priority tasks (priority = 1) in parallel.
    public List<Task> processHighPriorityTasks() {
        return taskRepository.findAll().stream()
                .parallel()
                .filter(task -> task.getPriority() == 1)
                .peek(task -> System.out.println("Processing task: " + task.getTitle() + " on thread " + Thread.currentThread().getName()))
                .toList();
    }

    //Create a summary string for all tasks grouped by status.

    public String getTaskSummaryByStatus() {
        return taskRepository.findAll().stream()
                .collect(Collectors.groupingBy(Task::getStatus,
                        Collectors.mapping(Task::getTitle, Collectors.joining(", "))))
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
    }


}
