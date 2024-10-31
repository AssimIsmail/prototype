package com.backend.crm1.mapper;

import com.backend.crm1.dto.ClientsDTO;
import com.backend.crm1.model.Clients;
import com.backend.crm1.model.Centre;
import com.backend.crm1.model.Project;
import com.backend.crm1.model.Status;
import com.backend.crm1.model.User;
import com.backend.crm1.repository.CentreRepository;
import com.backend.crm1.repository.ProjectRepository;
import com.backend.crm1.repository.StatusRepository;
import com.backend.crm1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientsMapper {

    @Autowired
    private CentreRepository centreRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    public ClientsDTO toDTO(Clients clients) {
        if (clients == null) {
            return null;
        }

        ClientsDTO dto = new ClientsDTO();
        dto.setId(clients.getId());
        dto.setFirst_name(clients.getFirst_name());
        dto.setLast_name(clients.getLast_name());
        dto.setEmail(clients.getEmail());
        dto.setPhone(clients.getPhone());
        dto.setOtherphone(clients.getOtherphone());
        dto.setAddress(clients.getAddress());
        dto.setCommantaire_prospecteur(clients.getCommantaire_prospecteur());
        dto.setCommantaire_vendeur(clients.getCommantaire_vendeur());

        if (clients.getCentre() != null) {
            dto.setCentreId(clients.getCentre().getId());
        }
        if (clients.getProject() != null) {
            dto.setProjectId(clients.getProject().getId());
        }
        if (clients.getStatus() != null) {
            dto.setStatusId(clients.getStatus().getId());
        }
        if (clients.getProspecteur() != null) {
            dto.setProspecteurId(clients.getProspecteur().getId());
        }
        if (clients.getVendeur() != null) {
            dto.setVendeurId(clients.getVendeur().getId());
        }
        return dto;
    }

    public Clients toEntity(ClientsDTO clientsDTO) {
        if (clientsDTO == null) {
            return null;
        }

        Clients clients = new Clients();
        clients.setId(clientsDTO.getId());
        clients.setFirst_name(clientsDTO.getFirst_name());
        clients.setLast_name(clientsDTO.getLast_name());
        clients.setEmail(clientsDTO.getEmail());
        clients.setPhone(clientsDTO.getPhone());
        clients.setOtherphone(clientsDTO.getOtherphone());
        clients.setAddress(clientsDTO.getAddress());
        clients.setCommantaire_prospecteur(clientsDTO.getCommantaire_prospecteur());
        clients.setCommantaire_vendeur(clientsDTO.getCommantaire_vendeur());

        // Set related entities based on IDs from DTO
        if (clientsDTO.getCentreId() != null) {
            Centre centre = centreRepository.findById(clientsDTO.getCentreId()).orElse(null);
            clients.setCentre(centre);
        }

        if (clientsDTO.getProjectId() != null) {
            Project project = projectRepository.findById(clientsDTO.getProjectId()).orElse(null);
            clients.setProject(project);
        }

        if (clientsDTO.getStatusId() != null) {
            Status status = statusRepository.findById(clientsDTO.getStatusId()).orElse(null);
            clients.setStatus(status);
        }

        if (clientsDTO.getProspecteurId() != null) {
            User prospecteur = userRepository.findById(clientsDTO.getProspecteurId()).orElse(null);
            clients.setProspecteur(prospecteur);
        }

        if (clientsDTO.getVendeurId() != null) {
            User vendeur = userRepository.findById(clientsDTO.getVendeurId()).orElse(null);
            clients.setVendeur(vendeur);
        }

        return clients;
    }
}
