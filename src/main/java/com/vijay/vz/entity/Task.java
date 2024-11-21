package com.vijay.vz.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "task_collection")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String status; // e.g., "Pending", "Completed", "In Progress"
    private int priority; // 1 (High) to 5 (Low)
    private LocalDate dueDate;

}
