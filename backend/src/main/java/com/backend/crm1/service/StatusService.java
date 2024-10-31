package com.backend.crm1.service;

import com.backend.crm1.dto.StatusDTO;
import com.backend.crm1.mapper.StatusMapper;
import com.backend.crm1.model.Status;
import com.backend.crm1.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    // Create a new status
    public StatusDTO createStatus(StatusDTO statusDTO) {
        Status status = statusMapper.toEntity(statusDTO);
        Status savedStatus = statusRepository.save(status);
        return statusMapper.toDTO(savedStatus);
    }

    // Get all statuses
    public List<StatusDTO> getAllStatuses() {
        return statusRepository.findAll().stream()
                .map(statusMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a status by ID
    public StatusDTO getStatusById(Long statusId) {
        Optional<Status> status = statusRepository.findById(statusId);
        return status.map(statusMapper::toDTO).orElse(null);
    }

    // Update a status
    public StatusDTO updateStatus(Long statusId, StatusDTO statusDTO) {
        if (statusRepository.existsById(statusId)) {
            Status status = statusMapper.toEntity(statusDTO);
            status.setId(statusId);  // Ensure the ID is set
            Status updatedStatus = statusRepository.save(status);
            return statusMapper.toDTO(updatedStatus);
        }
        return null;
    }

    // Delete a status
    public void deleteStatus(Long statusId) {
        if (statusRepository.existsById(statusId)) {
            statusRepository.deleteById(statusId);
        } else {
            throw new IllegalArgumentException("Status with ID " + statusId + " does not exist.");
        }
    }
}
