package com.backend.crm1.dto;


import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class ProjectDTO {
    private long id;
    private String name;
    private String description;
    private Date start_date;
    private Date end_date;
    private Long centreId;
    private List<Long> statuts;
}
