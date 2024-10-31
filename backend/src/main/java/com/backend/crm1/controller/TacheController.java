package com.backend.crm1.controller;

import com.backend.crm1.dto.TacheDTO;
import com.backend.crm1.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
public class TacheController {

    @Autowired
    private TacheService tacheService;

    // Create a new task
    @PostMapping
    public ResponseEntity<TacheDTO> createTache(@RequestBody TacheDTO tacheDTO) {
        TacheDTO createdTache = tacheService.createTache(tacheDTO);
        return new ResponseEntity<>(createdTache, HttpStatus.CREATED);
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<TacheDTO>> getAllTaches() {
        List<TacheDTO> taches = tacheService.getAllTaches();
        return new ResponseEntity<>(taches, HttpStatus.OK);
    }

    // Get a task by ID
    @GetMapping("/{tacheId}")
    public ResponseEntity<TacheDTO> getTacheById(@PathVariable Long tacheId) {
        TacheDTO tache = tacheService.getTacheById(tacheId);
        return tache != null ? new ResponseEntity<>(tache, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update a task
    @PutMapping("/{tacheId}")
    public ResponseEntity<TacheDTO> updateTache(@PathVariable Long tacheId, @RequestBody TacheDTO tacheDTO) {
        TacheDTO updatedTache = tacheService.updateTache(tacheId, tacheDTO);
        return updatedTache != null ? new ResponseEntity<>(updatedTache, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a task
    @DeleteMapping("/{tacheId}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long tacheId) {
        try {
            tacheService.deleteTache(tacheId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get tasks by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TacheDTO>> getTachesByUserId(@PathVariable Long userId) {
        List<TacheDTO> taches = tacheService.getTachesByUserId(userId);
        return new ResponseEntity<>(taches, HttpStatus.OK);
    }
    @PatchMapping("/{tacheId}/trash")
    public ResponseEntity<TacheDTO> moveToTrash(@PathVariable Long tacheId) {
        TacheDTO trashedTache = tacheService.moveToTrash(tacheId);
        return trashedTache != null ? new ResponseEntity<>(trashedTache, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
