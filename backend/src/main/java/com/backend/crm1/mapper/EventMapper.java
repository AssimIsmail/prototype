package com.backend.crm1.mapper;

import com.backend.crm1.dto.EventDTO;
import com.backend.crm1.model.Event;
import com.backend.crm1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.backend.crm1.repository.UserRepository;

@Component
public class EventMapper {

    @Autowired
    private UserRepository userRepository;

    public EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setStart(event.getStart());
        dto.setEnd(event.getEnd());
        dto.setDescription(event.getDescription());
        dto.setType(event.getType());

        if (event.getUser() != null) {
            dto.setUserId(event.getUser().getId());
        }

        return dto;
    }

    public Event toEntity(EventDTO dto) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setId(dto.getId());
        event.setTitle(dto.getTitle());
        event.setStart(dto.getStart());
        event.setEnd(dto.getEnd());
        event.setDescription(dto.getDescription());
        event.setType(dto.getType());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            event.setUser(user);
        }

        return event;
    }
}
