package com.backend.crm1.repository;

import com.backend.crm1.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project ,Long> {
}
