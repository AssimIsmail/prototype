package com.backend.crm1.mapper;

import com.backend.crm1.dto.StatusDTO;
import com.backend.crm1.model.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public StatusDTO toDTO(Status status) {
        if (status == null) {
            return null;
        }

        StatusDTO dto = new StatusDTO();
        dto.setId(status.getId());
        dto.setName(status.getName());
        dto.setColor(status.getColor());
        return dto;
    }

    public Status toEntity(StatusDTO statusDTO) {
        if (statusDTO == null) {
            return null;
        }

        Status status = new Status();
        status.setId(statusDTO.getId());
        status.setName(statusDTO.getName());
        status.setColor(statusDTO.getColor());
        return status;
    }
}
