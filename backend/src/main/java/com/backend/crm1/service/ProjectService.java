package com.backend.crm1.service;

import com.backend.crm1.dto.ProjectDTO;
import com.backend.crm1.dto.StatusDTO;
import com.backend.crm1.mapper.ProjectMapper;
import com.backend.crm1.model.Project;
import com.backend.crm1.model.Status;
import com.backend.crm1.repository.ProjectRepository;
import com.backend.crm1.repository.StatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ProjectMapper projectMapper;

    // Create a new project
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    // Get all projects
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a project by ID as an entity
    public Project getProjectByIdAsEntity(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.orElse(null);
    }

    // Get a project by ID
    public ProjectDTO getProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.map(projectMapper::toDTO).orElse(null);
    }

    // Update a project
    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO) {
        if (projectRepository.existsById(projectId)) {
            Project project = projectMapper.toEntity(projectDTO);
            project.setId(projectId);  // Ensure the ID is updated
            Project updatedProject = projectRepository.save(project);
            return projectMapper.toDTO(updatedProject);
        }
        return null;
    }

    // Delete a project
    public void deleteProject(Long projectId) {
        if (projectRepository.existsById(projectId)) {
            projectRepository.deleteById(projectId);
        } else {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }
    }


    @Transactional
    public ProjectDTO creerProjetAvecStatut(Long idProject, Long idStatut) {
        // Récupérer le projet depuis la base de données
        Project project = projectRepository.findById(idProject)
                .orElseThrow(() -> new RuntimeException("Project non trouvé"));

        // Récupérer le statut depuis la base de données
        Status statut = statusRepository.findById(idStatut)
                .orElseThrow(() -> new RuntimeException("Statut non trouvé"));

        // Ajouter le statut au projet
        project.getStatuts().add(statut);

        // Enregistrer le projet, cela va aussi mettre à jour la table de jointure
        projectRepository.save(project);

        // Convertir l'entité Project en ProjectDTO avant de la retourner
        return convertToDTO(project);
    }

    // Méthode pour convertir un Project en ProjectDTO
    private ProjectDTO convertToDTO(Project project) {
        // Exemple de conversion, vous devrez adapter cela selon la structure de vos classes
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        // Ajoutez d'autres propriétés selon vos besoins
        return dto;
    }


    public List<ProjectDTO> getProjetsParStatut(Long idStatut) {
        Status statut = statusRepository.findById(idStatut)
                .orElseThrow(() -> new RuntimeException("Statut non trouvé"));

        // Convertir les projets en DTOs
        return statut.getProjets().stream()
                .map(projet -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setId(projet.getId());
                    dto.setName(projet.getName());
                    dto.setDescription(projet.getDescription());
                    dto.setStart_date(projet.getStart_date());
                    dto.setEnd_date(projet.getEnd_date());
                    dto.setCentreId(projet.getCentre().getId());
                    // Si tu souhaites aussi récupérer les statuts associés
                    dto.setStatuts(projet.getStatuts().stream()
                            .map(Status::getId) // Assurez-vous que tu veux juste les IDs des statuts
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList()); // Retourne une liste de ProjectDTO
    }


    // Méthode pour obtenir tous les statuts associés à un projet
    public List<StatusDTO> getStatutsParProjet(Long idProject) {
        Project projet = projectRepository.findById(idProject)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));
        return projet.getStatuts().stream()
                .map(statut -> {
                    StatusDTO dto = new StatusDTO();
                    dto.setId(statut.getId());
                    dto.setName(statut.getName());
                    dto.setColor(statut.getColor());
                    dto.setProjets(Collections.singletonList(projet.getId()));
                    return dto;
                })
                .collect(Collectors.toList()); // Retourne la liste des statuts liés à ce projet
    }

    @Transactional
    public void supprimerRelation(Long idProject, Long idStatut) {
        Project projet = projectRepository.findById(idProject)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        Status statut = statusRepository.findById(idStatut)
                .orElseThrow(() -> new RuntimeException("Statut non trouvé"));

        // Supprimer la relation
        projet.getStatuts().remove(statut); // Retire le statut du projet

        // Sauvegarder les modifications
        projectRepository.save(projet);
    }
}
