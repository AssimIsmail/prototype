package com.backend.crm1.service;

import com.backend.crm1.dto.TacheDTO;
import com.backend.crm1.mapper.TacheMapper;
import com.backend.crm1.model.Tache;
import com.backend.crm1.repository.TacheRepository;
import com.backend.crm1.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional // Assure que toutes les méthodes sont transactionnelles
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TacheMapper tacheMapper;

    // Create a new task
    public TacheDTO createTache(TacheDTO tacheDTO) {
        Tache tache = tacheMapper.toEntity(tacheDTO);
        Tache savedTache = tacheRepository.save(tache);
        return tacheMapper.toDTO(savedTache);
    }

    // Get all tasks
    public List<TacheDTO> getAllTaches() {
        return tacheRepository.findAll().stream()
                .map(tacheMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a task by ID
    public TacheDTO getTacheById(Long tacheId) {
        Optional<Tache> tache = tacheRepository.findById(tacheId);
        return tache.map(tacheMapper::toDTO).orElse(null);
    }

    // Get a task by ID as an entity
    public Tache getTacheByIdAsEntity(Long tacheId) {
        return tacheRepository.findById(tacheId).orElse(null);
    }

    // Update a task
    public TacheDTO updateTache(Long tacheId, TacheDTO tacheDTO) {
        // Check if the task exists
        if (tacheRepository.existsById(tacheId)) {
            Tache tache = tacheMapper.toEntity(tacheDTO);
            tache.setId(tacheId);  // Ensure the ID is set to the existing task's ID
            Tache updatedTache = tacheRepository.save(tache);
            return tacheMapper.toDTO(updatedTache);
        }
        return null; // Or throw an exception if preferred
    }

    // Delete a task
    public void deleteTache(Long tacheId) {
        // Check if the task exists before trying to delete
        if (tacheRepository.existsById(tacheId)) {
            tacheRepository.deleteById(tacheId);
        } else {
            throw new IllegalArgumentException("Task with ID " + tacheId + " does not exist.");
        }
    }

    // Get tasks by user ID
    public List<TacheDTO> getTachesByUserId(Long userId) {
        return tacheRepository.findByUserId(userId).stream()
                .map(tacheMapper::toDTO)
                .collect(Collectors.toList());
    }
    public TacheDTO moveToTrash(Long tacheId) {
        Optional<Tache> optionalTache = tacheRepository.findById(tacheId);
        if (optionalTache.isPresent()) {
            Tache tache = optionalTache.get();
            // Assuming there's a boolean field 'trashed' in Tache class
            tache.setTrashed(true);  // Mark the task as trashed
            Tache updatedTache = tacheRepository.save(tache);  // Save changes
            return tacheMapper.toDTO(updatedTache);
        }
        return null;  // Or throw an exception if preferred
    }
}
