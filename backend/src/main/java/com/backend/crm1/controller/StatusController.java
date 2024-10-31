package com.backend.crm1.controller;

import com.backend.crm1.dto.StatusDTO;
import com.backend.crm1.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    // Create a new status
    @PostMapping
    public ResponseEntity<StatusDTO> createStatus(@RequestBody StatusDTO statusDTO) {
        if (statusDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        StatusDTO createdStatus = statusService.createStatus(statusDTO);
        return new ResponseEntity<>(createdStatus, HttpStatus.CREATED);
    }

    // Get all statuses
    @GetMapping
    public ResponseEntity<List<StatusDTO>> getAllStatuses() {
        List<StatusDTO> statusDTOList = statusService.getAllStatuses();
        return new ResponseEntity<>(statusDTOList, HttpStatus.OK);
    }

    // Get a status by ID
    @GetMapping("/{statusId}")
    public ResponseEntity<StatusDTO> getStatusById(@PathVariable Long statusId) {
        StatusDTO statusDTO = statusService.getStatusById(statusId);
        if (statusDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(statusDTO);
    }

    // Update a status
    @PutMapping("/{statusId}")
    public ResponseEntity<StatusDTO> updateStatus(@PathVariable Long statusId, @RequestBody StatusDTO statusDTO) {
        StatusDTO updatedStatus = statusService.updateStatus(statusId, statusDTO);
        if (updatedStatus == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedStatus);
    }

    // Delete a status
    @DeleteMapping("/{statusId}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long statusId) {
        try {
            statusService.deleteStatus(statusId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
