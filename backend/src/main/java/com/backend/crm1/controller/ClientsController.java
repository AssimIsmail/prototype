package com.backend.crm1.controller;

import com.backend.crm1.dto.ClientsDTO;
import com.backend.crm1.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    // Create a new client
    @PostMapping
    public ResponseEntity<ClientsDTO> createClient(@RequestBody ClientsDTO clientsDTO) {
        if (clientsDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsDTO createdClient = clientsService.createClient(clientsDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    // Get all clients
    @GetMapping
    public ResponseEntity<List<ClientsDTO>> getAllClients() {
        List<ClientsDTO> clientsDTOList = clientsService.getAllClients();
        return new ResponseEntity<>(clientsDTOList, HttpStatus.OK);
    }

    // Get client by ID
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientsDTO> getClientById(@PathVariable Long clientId) {
        if (clientId == null || clientId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsDTO clientsDTO = clientsService.getClientById(clientId);
        if (clientsDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(clientsDTO);
    }

    // Update a client
    @PutMapping("/{clientId}")
    public ResponseEntity<ClientsDTO> updateClient(@PathVariable Long clientId, @RequestBody ClientsDTO clientsDTO) {
        if (clientsDTO == null || clientId == null || clientId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsDTO updatedClient = clientsService.updateClient(clientId, clientsDTO);
        if (updatedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedClient);
    }

    // Delete a client
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        if (clientId == null || clientId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (clientsService.getClientById(clientId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientsService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }
}
