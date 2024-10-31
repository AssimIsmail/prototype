package com.backend.crm1.service;

import com.backend.crm1.dto.EventDTO;
import com.backend.crm1.mapper.EventMapper;
import com.backend.crm1.model.Event;
import com.backend.crm1.repository.EventRepository;
import com.backend.crm1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventMapper eventMapper;

    // Create a new event
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = eventMapper.toEntity(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDTO(savedEvent);
    }

    // Get all events
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get an event by ID
    public EventDTO getEventById(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        return event.map(eventMapper::toDTO).orElse(null);
    }

    // Get an event by ID as an entity
    public Event getEventByIdAsEntity(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    // Update an event
    public EventDTO updateEvent(Long eventId, EventDTO eventDTO) {
        if (eventRepository.existsById(eventId)) {
            Event event = eventMapper.toEntity(eventDTO);
            event.setId(eventId);  // Ensure the ID is updated
            Event updatedEvent = eventRepository.save(event);
            return eventMapper.toDTO(updatedEvent);
        }
        return null;
    }

    // Delete an event
    public void deleteEvent(Long eventId) {
        if (eventRepository.existsById(eventId)) {
            eventRepository.deleteById(eventId);
        } else {
            throw new IllegalArgumentException("Event with ID " + eventId + " does not exist.");
        }
    }

    // Get events by user ID
    public List<EventDTO> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId).stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }
}
