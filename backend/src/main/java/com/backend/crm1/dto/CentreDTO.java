package com.backend.crm1.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CentreDTO {
    private long id;
    private String name;
    private String location;
    private String phone;
    private String email;
    private String logo;
}
