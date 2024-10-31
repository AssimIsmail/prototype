package com.backend.crm1.repository;

import com.backend.crm1.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document , Long> {
    List<Document> findByUserId(Long userId);
}
