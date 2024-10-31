package com.backend.crm1.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TacheDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private String priority;
    private String status;
    private Long userId;
    private String tag;
    private String path;
    private String assignee;
    private boolean trashed; // New field to match the Tache model

}
