package com.backend.crm1.dto;

import lombok.Data;

@Data
public class ClientsDTO {
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String otherphone;
    private String address;
    private String commantaire_prospecteur;
    private String commantaire_vendeur;
    private Long centreId;
    private Long projectId;
    private Long statusId;
    private Long prospecteurId;
    private Long vendeurId;
}
