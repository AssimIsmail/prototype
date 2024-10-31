package com.backend.crm1.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private String type;
    private Long userId;
}
