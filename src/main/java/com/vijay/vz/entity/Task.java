package com.vijay.vz.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String status;

}
