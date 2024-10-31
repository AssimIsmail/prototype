package com.backend.crm1.controller;

import com.backend.crm1.dto.ProjectDTO;
import com.backend.crm1.dto.StatusDTO;
import com.backend.crm1.model.Project;
import com.backend.crm1.model.Status;
import com.backend.crm1.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Create a project
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProjectDTO createdProject = projectService.createProject(projectDTO);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projectDTOList = projectService.getAllProjects();
        return new ResponseEntity<>(projectDTOList, HttpStatus.OK);
    }

    // Get a project by ID
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long projectId) {
        if (projectId == null || projectId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProjectDTO projectDTO = projectService.getProjectById(projectId);
        if (projectDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(projectDTO);
    }

    // Update a project
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long projectId, @RequestBody ProjectDTO projectDTO) {
        if (projectDTO == null || projectId == null || projectId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProjectDTO updatedProject = projectService.updateProject(projectId, projectDTO);
        if (updatedProject == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedProject);
    }

    // Delete a project
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        if (projectId == null || projectId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (projectService.getProjectById(projectId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/creerProjetAvecStatut")
    public ResponseEntity<ProjectDTO> creerProjet(@RequestBody Map<String, Long> params) {
        Long idProject = params.get("idProject");
        Long idStatut = params.get("idStatut");

        ProjectDTO createdProject = projectService.creerProjetAvecStatut(idProject, idStatut);

        if (createdProject != null) {
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/projetsParStatut")
    public ResponseEntity<List<ProjectDTO>> getProjetsParStatut(@RequestParam Long idStatut) {
        List<ProjectDTO> projets = projectService.getProjetsParStatut(idStatut);

        if (!projets.isEmpty()) {
            return new ResponseEntity<>(projets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(projets, HttpStatus.NO_CONTENT); // Ou un message personnalisé
        }
    }

    // Endpoint pour obtenir tous les statuts associés à un projet
    @GetMapping("/statutsParProjet")
    public ResponseEntity<List<StatusDTO>> getStatutsParProjet(@RequestParam Long idProject) {
        List<StatusDTO> statuts = projectService.getStatutsParProjet(idProject);

        if (!statuts.isEmpty()) {
            return new ResponseEntity<>(statuts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(statuts, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{idProject}/statuts/{idStatut}")
    public ResponseEntity<String> supprimerRelation(@PathVariable Long idProject, @PathVariable Long idStatut) {
        projectService.supprimerRelation(idProject, idStatut);

        return new ResponseEntity<>("La relation entre le projet et le statut a été supprimée avec succès.", HttpStatus.NO_CONTENT);
    }




}
