package com.backend.crm1.mapper;

import com.backend.crm1.dto.ProjectDTO;
import com.backend.crm1.model.Project;
import com.backend.crm1.model.Centre;
import com.backend.crm1.repository.CentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    @Autowired
    private CentreRepository centreRepository;

    public ProjectDTO toDTO(Project project) {
        if (project == null) {
            return null;
        }

        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStart_date(project.getStart_date());
        dto.setEnd_date(project.getEnd_date());
        if (project.getCentre() != null) {
            dto.setCentreId(project.getCentre().getId());
        }

        return dto;
    }

    // Converts a ProjectDTO to Project entity
    public Project toEntity(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }

        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStart_date(projectDTO.getStart_date());
        project.setEnd_date(projectDTO.getEnd_date());

        // Associate the Project with a Centre entity if centreId is provided
        if (projectDTO.getCentreId() != null) {
            Centre centre = centreRepository.findById(projectDTO.getCentreId()).orElse(null);
            project.setCentre(centre);
        }

        return project;
    }
}
