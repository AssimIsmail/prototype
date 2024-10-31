package com.backend.crm1.controller;

import com.backend.crm1.dto.DocumentDTO;
import com.backend.crm1.service.DocumentService;
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
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @Value("${upload.dir.documents}")
    private String uploadDir;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentDTO> createDocument(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setName(name);
        documentDTO.setType(type);
        documentDTO.setUserId(userId);

        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = new File(uploadDir, fileName).toPath();
            Files.copy(file.getInputStream(), filePath);
            documentDTO.setUrl("uploads/documents/" + fileName);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DocumentDTO createdDocument = documentService.createDocument(documentDTO);
        return new ResponseEntity<>(createdDocument, HttpStatus.CREATED);
    }
    // Retrieve all documents
    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
        List<DocumentDTO> documentDTOList = documentService.getAllDocuments();
        return new ResponseEntity<>(documentDTOList, HttpStatus.OK);
    }

    // Retrieve a document by ID
    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable Long documentId) {
        if (documentId == null || documentId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        DocumentDTO documentDTO = documentService.getDocumentById(documentId);
        if (documentDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(documentDTO);
    }

    // Update a document with the option to change the file
    @PutMapping(value = "/{documentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentDTO> updateDocument(
            @PathVariable Long documentId,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        DocumentDTO existingDocument = documentService.getDocumentById(documentId);
        if (existingDocument == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(documentId);
        documentDTO.setName(name);
        documentDTO.setType(type);
        documentDTO.setUserId(userId);

        // If a new file is provided
        if (file != null && !file.isEmpty()) {
            try {
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = new File(uploadDir, fileName).toPath();
                Files.copy(file.getInputStream(), filePath);

                documentDTO.setUrl("uploads/documents/" + fileName); // Update file URL
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Keep the old document URL
            documentDTO.setUrl(existingDocument.getUrl());
        }

        DocumentDTO updatedDocument = documentService.updateDocument(documentId, documentDTO);
        return ResponseEntity.ok(updatedDocument);
    }

    // Delete a document
    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        if (documentId == null || documentId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (documentService.getDocumentById(documentId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public List<DocumentDTO> getDocumentsByUserId(@PathVariable Long userId) {
        return documentService.getDocumentsByUserId(userId);
    }
}
