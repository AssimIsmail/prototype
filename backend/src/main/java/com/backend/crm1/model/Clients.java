package com.backend.crm1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "otherphone")
    private String otherphone;

    @Column(name = "address")
    private String address;

    @Column(name = "commantaire_prospecteur")
    private String commantaire_prospecteur;

    @Column(name = "commantaire_vendeur")
    private String commantaire_vendeur;

    @ManyToOne
    @JoinColumn(name = "centre_id", referencedColumnName = "id")
    private Centre centre;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "prospecteur_id", referencedColumnName = "id")
    private User prospecteur;
    @ManyToOne
    @JoinColumn(name = "vendeur_id", referencedColumnName = "id")
    private User vendeur;
    @OneToMany(mappedBy = "clients", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enregistrement> enregistrements = new ArrayList<>();
}
