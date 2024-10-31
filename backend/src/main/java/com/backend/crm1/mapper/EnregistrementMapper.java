package com.backend.crm1.mapper;

import com.backend.crm1.dto.EnregistrementDTO;
import com.backend.crm1.model.Enregistrement;
import com.backend.crm1.model.User;
import com.backend.crm1.model.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.backend.crm1.repository.UserRepository;
import com.backend.crm1.repository.ClientsRepository;

@Component
public class EnregistrementMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    public EnregistrementDTO toDTO(Enregistrement enregistrement) {
        if (enregistrement == null) {
            return null;
        }

        EnregistrementDTO dto = new EnregistrementDTO();
        dto.setId(enregistrement.getId());
        dto.setUrl(enregistrement.getUrl());

        if (enregistrement.getUser() != null) {
            dto.setUserId(enregistrement.getUser().getId());
        }

        if (enregistrement.getClients() != null) {
            dto.setClientId(enregistrement.getClients().getId());
        }

        return dto;
    }

    public Enregistrement toEntity(EnregistrementDTO dto) {
        if (dto == null) {
            return null;
        }

        Enregistrement enregistrement = new Enregistrement();
        enregistrement.setId(dto.getId());
        enregistrement.setUrl(dto.getUrl());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            enregistrement.setUser(user);
        }

        if (dto.getClientId() != null) {
            Clients client = clientsRepository.findById(dto.getClientId()).orElse(null);
            enregistrement.setClients(client);
        }

        return enregistrement;
    }
}
