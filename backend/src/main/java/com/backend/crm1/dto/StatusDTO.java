package com.backend.crm1.dto;


import lombok.Data;

import java.util.List;

@Data
public class StatusDTO {
    private long id;
    private String name ;
    private String color ;
    private List<Long> projets;
}
