package com.backend.crm1.dto;


import lombok.Data;

@Data
public class DocumentDTO {
    private long id;
    private String name;
    private String type;
    private String url;
    private Long userId;
}
