package com.backend.crm1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "taches")
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "priority")
    private String priority;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "tag")
    private String tag;

    @Column(name = "path")
    private String path;

    @Column(name = "assignee")
    private String assignee;
    @Column(name = "trashed") // New field to indicate if the task is trashed
    private boolean trashed; // Default is false
}
