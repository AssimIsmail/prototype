package com.backend.crm1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class FileController {

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        File file = new File(uploadDir + "/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // Return 404 if the file doesn't exist
        }
        return ResponseEntity.ok(new FileSystemResource(file));
    }

    @GetMapping("/uploads/documents/{documentName:.+}")
    public ResponseEntity<Resource> getDocument(@PathVariable String documentName) {
        File file = new File(uploadDir + "/documents/" + documentName);
        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // Return 404 if the document doesn't exist
        }
        // Ensure the content type is correct
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF) // Set content type as PDF
                .body(new FileSystemResource(file)); // Serve the document as a resource
    }

    @GetMapping("/uploads/enregistrements/{filename:.+}")
    public ResponseEntity<Resource> getEnregistrement(@PathVariable String filename) {
        File file = new File(uploadDir + "/enregistrements/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // Return 404 if the file doesn't exist
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Set content type as generic binary stream
                .body(new FileSystemResource(file)); // Serve the audio file as a resource
    }
}
