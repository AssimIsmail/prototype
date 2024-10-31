package com.backend.crm1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.crm1.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}