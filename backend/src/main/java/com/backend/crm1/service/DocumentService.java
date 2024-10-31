package com.backend.crm1.service;

import com.backend.crm1.dto.DocumentDTO;
import com.backend.crm1.mapper.DocumentMapper;
import com.backend.crm1.model.Document;
import com.backend.crm1.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentMapper documentMapper;

    // Créer un document
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        Document document = documentMapper.toEntity(documentDTO);
        Document savedDocument = documentRepository.save(document);
        return documentMapper.toDTO(savedDocument);
    }

    // Récupérer tous les documents
    public List<DocumentDTO> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un document par ID en tant qu'entité
    public Document getDocumentByIdAsEntity(Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        return document.orElse(null);
    }

    // Récupérer un document par ID
    public DocumentDTO getDocumentById(Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        return document.map(documentMapper::toDTO).orElse(null);
    }

    // Mettre à jour un document
    public DocumentDTO updateDocument(Long documentId, DocumentDTO documentDTO) {
        if (documentRepository.existsById(documentId)) {
            Document document = documentMapper.toEntity(documentDTO);
            document.setId(documentId);  // Assurez-vous que l'ID est mis à jour
            Document updatedDocument = documentRepository.save(document);
            return documentMapper.toDTO(updatedDocument);
        }
        return null;
    }

    // Supprimer un document
    public void deleteDocument(Long documentId) {
        if (documentRepository.existsById(documentId)) {
            documentRepository.deleteById(documentId);
        } else {
            throw new IllegalArgumentException("Document with ID " + documentId + " does not exist.");
        }
    }
    public List<DocumentDTO> getDocumentsByUserId(Long userId) {
        return documentRepository.findByUserId(userId).stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
