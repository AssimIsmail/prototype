package com.backend.crm1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name" ,unique = true)
    private String name ;
    @Column(name = "color" ,unique = true)
    private String color ;
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clients> clients = new ArrayList<>();

    @ManyToMany(mappedBy = "statuts")
    private List<Project> projets;
}