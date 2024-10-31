package com.backend.crm1.controller;

import com.backend.crm1.dto.EventDTO;
import com.backend.crm1.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Create an event
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        if (eventDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Get an event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId) {
        if (eventId == null || eventId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EventDTO eventDTO = eventService.getEventById(eventId);
        if (eventDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(eventDTO);
    }

    // Update an event
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId, @RequestBody EventDTO eventDTO) {
        if (eventDTO == null || eventId == null || eventId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EventDTO updatedEvent = eventService.updateEvent(eventId, eventDTO);
        if (updatedEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedEvent);
    }

    // Delete an event
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        if (eventId == null || eventId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (eventService.getEventById(eventId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventDTO>> getEventsByUserId(@PathVariable Long userId) {
        if (userId == null || userId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<EventDTO> events = eventService.getEventsByUserId(userId);
        if (!events.isEmpty()) {
            return new ResponseEntity<>(events, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(events, HttpStatus.NO_CONTENT);
        }
    }
}
