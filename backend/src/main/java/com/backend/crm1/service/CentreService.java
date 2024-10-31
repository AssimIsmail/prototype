package com.backend.crm1.service;

import com.backend.crm1.dto.CentreDTO;
import com.backend.crm1.mapper.CentreMapper;
import com.backend.crm1.model.Centre;
import com.backend.crm1.repository.CentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CentreService {

    @Autowired
    private CentreRepository centreRepository;

    @Autowired
    private CentreMapper centreMapper;

    public CentreDTO createCentre(CentreDTO centreDTO) {
        Centre centre = centreMapper.toEntity(centreDTO);
        Centre savedCentre = centreRepository.save(centre);
        return centreMapper.toDTO(savedCentre);
    }

    public List<CentreDTO> getAllCentres() {
        return centreRepository.findAll().stream()
                .map(centreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Centre getCentreByIdAsEntity(Long centreId) {
        Optional<Centre> centre = centreRepository.findById(centreId);
        return centre.orElse(null);
    }

    public CentreDTO getCentreById(Long centreId) {
        Optional<Centre> centre = centreRepository.findById(centreId);
        return centre.map(centreMapper::toDTO).orElse(null);
    }

    public CentreDTO updateCentre(Long centreId, CentreDTO centreDTO) {
        if (centreRepository.existsById(centreId)) {
            Centre centre = centreMapper.toEntity(centreDTO);
            centre.setId(centreId);
            Centre updatedCentre = centreRepository.save(centre);
            return centreMapper.toDTO(updatedCentre);
        }
        return null;
    }

    public void deleteCentre(Long centreId) {
        if (centreRepository.existsById(centreId)) {
            centreRepository.deleteById(centreId);
        } else {
            throw new IllegalArgumentException("Centre with ID " + centreId + " does not exist.");
        }
    }
}
