package com.backend.crm1.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "enregistrements")
public class Enregistrement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "url")
    private String url;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Clients clients;
}
