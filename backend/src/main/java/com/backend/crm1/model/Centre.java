package com.backend.crm1.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "centres")
public class Centre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "logo")
    private String logo;
    @OneToMany(mappedBy = "centre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy = "centre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();
    @OneToMany(mappedBy = "centre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clients> clients = new ArrayList<>();

}
