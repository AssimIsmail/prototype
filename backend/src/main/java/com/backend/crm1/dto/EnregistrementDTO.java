package com.backend.crm1.dto;

import lombok.Data;

@Data
public class EnregistrementDTO {
    private long id;
    private String url;
    private Long userId;
    private Long clientId;
}
