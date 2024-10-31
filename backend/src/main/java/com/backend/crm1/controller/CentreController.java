package com.backend.crm1.controller;

import com.backend.crm1.dto.CentreDTO;
import com.backend.crm1.service.CentreService;
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
@RequestMapping("/api/centres")
public class CentreController {

    @Autowired
    private CentreService centreService;

    @Value("${upload.dir}")
    private String uploadDir; // Chemin du dossier de stockage d'images

    // Autres méthodes...

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CentreDTO> createCentre(
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {

        CentreDTO centreDTO = new CentreDTO();
        centreDTO.setName(name);
        centreDTO.setLocation(location);
        centreDTO.setPhone(phone);
        centreDTO.setEmail(email);

        // Si un logo est fourni, le stocker dans le dossier spécifié
        if (logo != null && !logo.isEmpty()) {
            try {
                // Créer le dossier si nécessaire
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Créer un nom de fichier unique
                String fileName = System.currentTimeMillis() + "_" + logo.getOriginalFilename();
                Path filePath = new File(uploadDir, fileName).toPath();
                // Sauvegarder le fichier
                Files.copy(logo.getInputStream(), filePath);

                // Enregistrer l'URL de l'image dans la base de données
                centreDTO.setLogo("uploads/" + fileName); // L'URL relative
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        CentreDTO createdCentre = centreService.createCentre(centreDTO);
        return new ResponseEntity<>(createdCentre, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CentreDTO>> getAllCentres() {
        List<CentreDTO> centreDTOList = centreService.getAllCentres();
        return new ResponseEntity<>(centreDTOList, HttpStatus.OK);
    }

    @GetMapping("/{centreId}")
    public ResponseEntity<CentreDTO> getCentreById(@PathVariable Long centreId) {
        if (centreId == null || centreId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CentreDTO centreDTO = centreService.getCentreById(centreId);
        if (centreDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(centreDTO);
    }

    @PutMapping(value = "/{centreId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CentreDTO> updateCentre(
            @PathVariable Long centreId,
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {

        // Récupérer le centre existant
        CentreDTO existingCentre = centreService.getCentreById(centreId);
        if (existingCentre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CentreDTO centreDTO = new CentreDTO();
        centreDTO.setId(centreId);
        centreDTO.setName(name);
        centreDTO.setLocation(location);
        centreDTO.setPhone(phone);
        centreDTO.setEmail(email);

        // Vérifiez si un nouveau logo a été fourni
        if (logo != null && !logo.isEmpty()) {
            // Gérer le stockage du nouveau logo
            try {
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + logo.getOriginalFilename();
                Path filePath = new File(uploadDir, fileName).toPath();
                Files.copy(logo.getInputStream(), filePath);

                centreDTO.setLogo("uploads/" + fileName); // Mettre à jour le logo
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Conserver l'ancien logo
            centreDTO.setLogo(existingCentre.getLogo());
        }

        CentreDTO updatedCentre = centreService.updateCentre(centreId, centreDTO);
        if (updatedCentre == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedCentre);
    }
    @DeleteMapping("/{centreId}")
    public ResponseEntity<Void> deleteCentre(@PathVariable Long centreId) {
        if (centreId == null || centreId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (centreService.getCentreById(centreId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        centreService.deleteCentre(centreId);
        return ResponseEntity.noContent().build();
    }
}
