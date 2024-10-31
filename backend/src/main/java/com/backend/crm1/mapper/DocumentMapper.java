package com.backend.crm1.mapper;


import com.backend.crm1.dto.DocumentDTO;
import com.backend.crm1.dto.UserDTO;
import com.backend.crm1.model.Centre;
import com.backend.crm1.model.Document;
import com.backend.crm1.model.User;
import com.backend.crm1.repository.CentreRepository;
import com.backend.crm1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public DocumentDTO toDTO(Document document) {
        if (document == null) {
            return null;
        }

        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setType(document.getType());
        dto.setName(document.getName());
        dto.setUrl(document.getUrl());
        if (document.getUser() != null) {
            dto.setUserId(document.getUser().getId());
        }
    return dto;
    }

    @Autowired
    private UserRepository userRepository;
    public Document toEntity(DocumentDTO documentDTO) {
        if (documentDTO == null) {
            return null;
        }
        Document document = new Document();

        document.setId(documentDTO.getId());
        document.setUrl(documentDTO.getUrl());
        document.setType(documentDTO.getType());
        document.setName(documentDTO.getName());
        if (documentDTO.getUserId() != null) {
            User user = userRepository.findById(documentDTO.getUserId()).orElse(null);
            document.setUser(user);
        }
        return document;

    }
}
