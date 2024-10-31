package com.backend.crm1.mapper;

import com.backend.crm1.dto.TacheDTO;
import com.backend.crm1.model.Tache;
import com.backend.crm1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.backend.crm1.repository.UserRepository;

@Component
public class TacheMapper {

    @Autowired
    private UserRepository userRepository;

    public TacheDTO toDTO(Tache tache) {
        if (tache == null) {
            return null;
        }

        TacheDTO dto = new TacheDTO();
        dto.setId(tache.getId());
        dto.setTitle(tache.getTitle());
        dto.setDescription(tache.getDescription());
        dto.setDate(tache.getDate());
        dto.setPriority(tache.getPriority());
        dto.setStatus(tache.getStatus());

        if (tache.getUser() != null) {
            dto.setUserId(tache.getUser().getId());
        }

        dto.setTag(tache.getTag());
        dto.setPath(tache.getPath());
        dto.setAssignee(tache.getAssignee());
        dto.setTrashed(tache.isTrashed()); // Map the trashed field

        return dto;
    }

    public Tache toEntity(TacheDTO dto) {
        if (dto == null) {
            return null;
        }

        Tache tache = new Tache();
        tache.setId(dto.getId());
        tache.setTitle(dto.getTitle());
        tache.setDescription(dto.getDescription());
        tache.setDate(dto.getDate());
        tache.setPriority(dto.getPriority());
        tache.setStatus(dto.getStatus());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            tache.setUser(user);
        }

        tache.setTag(dto.getTag());
        tache.setPath(dto.getPath());
        tache.setAssignee(dto.getAssignee());
        tache.setTrashed(dto.isTrashed()); // Map the trashed field

        return tache;
    }
}
