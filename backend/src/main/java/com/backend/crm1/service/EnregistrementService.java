package com.backend.crm1.service;

import com.backend.crm1.dto.EnregistrementDTO;
import com.backend.crm1.mapper.EnregistrementMapper;
import com.backend.crm1.model.Enregistrement;
import com.backend.crm1.repository.EnregistrementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnregistrementService {

    @Autowired
    private EnregistrementRepository enregistrementRepository;

    @Autowired
    private EnregistrementMapper enregistrementMapper;

    // Create a new enregistrement
    public EnregistrementDTO createEnregistrement(EnregistrementDTO enregistrementDTO) {
        Enregistrement enregistrement = enregistrementMapper.toEntity(enregistrementDTO);
        Enregistrement savedEnregistrement = enregistrementRepository.save(enregistrement);
        return enregistrementMapper.toDTO(savedEnregistrement);
    }

    // Get all enregistrements
    public List<EnregistrementDTO> getAllEnregistrements() {
        return enregistrementRepository.findAll().stream()
                .map(enregistrementMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get an enregistrement by ID
    public EnregistrementDTO getEnregistrementById(Long id) {
        Optional<Enregistrement> enregistrement = enregistrementRepository.findById(id);
        return enregistrement.map(enregistrementMapper::toDTO).orElse(null);
    }

    // Update an enregistrement
    public EnregistrementDTO updateEnregistrement(Long id, EnregistrementDTO enregistrementDTO) {
        if (enregistrementRepository.existsById(id)) {
            Enregistrement enregistrement = enregistrementMapper.toEntity(enregistrementDTO);
            enregistrement.setId(id); // Ensure ID is set
            Enregistrement updatedEnregistrement = enregistrementRepository.save(enregistrement);
            return enregistrementMapper.toDTO(updatedEnregistrement);
        }
        return null;
    }

    // Delete an enregistrement
    public void deleteEnregistrement(Long id) {
        if (enregistrementRepository.existsById(id)) {
            enregistrementRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Enregistrement with ID " + id + " does not exist.");
        }
    }
}
