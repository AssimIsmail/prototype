package com.backend.crm1.repository;

import com.backend.crm1.model.Enregistrement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnregistrementRepository extends JpaRepository<Enregistrement, Long> {
}
