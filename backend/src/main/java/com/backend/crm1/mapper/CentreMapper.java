package com.backend.crm1.mapper;

import com.backend.crm1.dto.CentreDTO;
import com.backend.crm1.dto.UserDTO;
import com.backend.crm1.model.Centre;
import org.springframework.stereotype.Component;

@Component
public class CentreMapper {

    public CentreDTO toDTO(Centre centre) {
        if (centre == null) {
            return null;
        }
        CentreDTO dto = new CentreDTO();
        dto.setId(centre.getId());
        dto.setPhone(centre.getPhone());
        dto.setName(centre.getName());
        dto.setLogo(centre.getLogo());
        dto.setEmail(centre.getEmail());
        dto.setLocation(centre.getLocation());
        return dto;
    }

    public Centre toEntity(CentreDTO centreDTO) {
        if (centreDTO == null) {
            return null;
        }
        Centre centre = new Centre();
        centre.setId(centreDTO.getId());
        centre.setPhone(centreDTO.getPhone());
        centre.setName(centreDTO.getName());
        centre.setLogo(centreDTO.getLogo());
        centre.setEmail(centreDTO.getEmail());
        centre.setLocation(centreDTO.getLocation());
        return centre;
    }
}
