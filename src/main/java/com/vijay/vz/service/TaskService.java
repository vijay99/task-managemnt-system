package com.vijay.vz.service;

import com.vijay.vz.entity.Task;
import com.vijay.vz.exception.BadRequestException;
import com.vijay.vz.exception.ResourceNotFoundException;
import com.vijay.vz.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public Task createTask(Task task){
        if(task.getTitle()==null || task.getTitle().isBlank()){
            throw new BadRequestException("Task title can't be empty");
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task updateTask(String id,Task updatedTask){
        Optional<Task> optionalTask= taskRepository.findById(id);

        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());

            return taskRepository.save(task);

        }
        throw new RuntimeException("Task not found");
    }

    public void deleteTask(String id){
        taskRepository.deleteById(id);
        System.out.println("I am after deleteing....");
    }

    public List<Task> getPendingTasks(){
        Optional<List<Task>> allTask= Optional.of(taskRepository.findAll());
        List<Task> pendingTasks = null;
        if(allTask.isPresent()){
            pendingTasks=allTask.get().stream().filter( task -> task.getStatus().equalsIgnoreCase("PENDING")).toList();
        }
      return pendingTasks;
    }

    public Task getTaskById(String id) {

        return taskRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Task with id - "+id+" not found"));
    }

    /*public Task getTaskById(String id) {
        Optional<Task> optionalTask= taskRepository.findById(id);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }else{
            throw new ResourceNotFoundException("Task with id - "+id+" not found");
        }
    }*/
}
