package com.backend.crm1.service;

import com.backend.crm1.dto.ClientsDTO;
import com.backend.crm1.mapper.ClientsMapper;
import com.backend.crm1.model.Clients;
import com.backend.crm1.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ClientsMapper clientsMapper;

    // Create a new client
    public ClientsDTO createClient(ClientsDTO clientsDTO) {
        Clients clients = clientsMapper.toEntity(clientsDTO);
        Clients savedClient = clientsRepository.save(clients);
        return clientsMapper.toDTO(savedClient);
    }

    // Get all clients
    public List<ClientsDTO> getAllClients() {
        return clientsRepository.findAll().stream()
                .map(clientsMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get client by ID
    public ClientsDTO getClientById(Long clientId) {
        Optional<Clients> client = clientsRepository.findById(clientId);
        return client.map(clientsMapper::toDTO).orElse(null);
    }

    // Update a client
    public ClientsDTO updateClient(Long clientId, ClientsDTO clientsDTO) {
        if (clientsRepository.existsById(clientId)) {
            Clients clients = clientsMapper.toEntity(clientsDTO);
            clients.setId(clientId);  // Ensure the ID is set
            Clients updatedClient = clientsRepository.save(clients);
            return clientsMapper.toDTO(updatedClient);
        }
        return null;
    }

    // Delete a client
    public void deleteClient(Long clientId) {
        if (clientsRepository.existsById(clientId)) {
            clientsRepository.deleteById(clientId);
        } else {
            throw new IllegalArgumentException("Client with ID " + clientId + " does not exist.");
        }
    }
}
