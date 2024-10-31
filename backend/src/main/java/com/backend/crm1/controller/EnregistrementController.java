package com.backend.crm1.controller;

import com.backend.crm1.dto.EnregistrementDTO;
import com.backend.crm1.service.EnregistrementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/enregistrements")
public class EnregistrementController {

    @Autowired
    private EnregistrementService enregistrementService;

    @Value("${upload.dir.enregistrements}")
    private String uploadDir;

    // Create a new enregistrement with audio file
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EnregistrementDTO> createEnregistrement(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("clientId") Long clientId) {

        if (file == null || file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EnregistrementDTO enregistrementDTO = new EnregistrementDTO();
        enregistrementDTO.setUserId(userId);
        enregistrementDTO.setClientId(clientId);

        // Handle file saving
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = new File(uploadDir, fileName).toPath();
            Files.copy(file.getInputStream(), filePath);
            enregistrementDTO.setUrl("uploads/enregistrements/" + fileName);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        EnregistrementDTO createdEnregistrement = enregistrementService.createEnregistrement(enregistrementDTO);
        return new ResponseEntity<>(createdEnregistrement, HttpStatus.CREATED);
    }

    // Get all enregistrements
    @GetMapping
    public ResponseEntity<List<EnregistrementDTO>> getAllEnregistrements() {
        List<EnregistrementDTO> enregistrementDTOList = enregistrementService.getAllEnregistrements();
        return new ResponseEntity<>(enregistrementDTOList, HttpStatus.OK);
    }

    // Get an enregistrement by ID
    @GetMapping("/{id}")
    public ResponseEntity<EnregistrementDTO> getEnregistrementById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EnregistrementDTO enregistrementDTO = enregistrementService.getEnregistrementById(id);
        if (enregistrementDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(enregistrementDTO);
    }

    // Update an enregistrement
    @PutMapping("/{id}")
    public ResponseEntity<EnregistrementDTO> updateEnregistrement(@PathVariable Long id, @RequestBody EnregistrementDTO enregistrementDTO) {
        if (enregistrementDTO == null || id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EnregistrementDTO updatedEnregistrement = enregistrementService.updateEnregistrement(id, enregistrementDTO);
        if (updatedEnregistrement == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedEnregistrement);
    }

    // Delete an enregistrement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnregistrement(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (enregistrementService.getEnregistrementById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        enregistrementService.deleteEnregistrement(id);
        return ResponseEntity.noContent().build();
    }
}
