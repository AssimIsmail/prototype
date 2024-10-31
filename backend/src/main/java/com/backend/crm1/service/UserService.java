package com.backend.crm1.service;

import com.backend.crm1.dto.UserDTO;
import com.backend.crm1.mapper.UserMapper;
import com.backend.crm1.model.Centre;
import com.backend.crm1.model.User;
import com.backend.crm1.repository.CentreRepository;
import com.backend.crm1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CentreRepository centreRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all users
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    // Get user by ID
    public UserDTO getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDTO).orElse(null); // ou vous pouvez lancer une exception
    }

    // Delete user by ID
    @Transactional
    public void deleteUser(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }
    // UserService.java
    public UserDTO updateUserPassword(long id, String newPassword) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        existingUser.setPassword(passwordEncoder.encode(newPassword)); // Hash the new password
        User updatedUser= userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);

    }



    public UserDTO updateUser(long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Mapper les champs de l'utilisateur DTO à l'utilisateur existant
        User userToUpdate = userMapper.toEntity(userDTO);

        // Mettre à jour les champs de l'utilisateur existant
        existingUser.setFirst_name(userToUpdate.getFirst_name());
        existingUser.setLast_name(userToUpdate.getLast_name());
        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setPhone(userToUpdate.getPhone());
        existingUser.setProfile(userToUpdate.getProfile());

        // Appliquer le mot de passe seulement si spécifié
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword())); // On hache le mot de passe
        }

        // Vérifier le centre uniquement s'il est spécifié dans le DTO
        if (userDTO.getCentreId() != null) {
            Centre centre = centreRepository.findById(userDTO.getCentreId()).orElse(null);
            existingUser.setCentre(centre);
        }

        // Enregistrer les modifications
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);

    }

// UserService.java

    // Nouvelle méthode pour mettre à jour uniquement le profil sans modifier le mot de passe
    public UserDTO updateUserProfile(long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        // Mapper les champs de l'utilisateur DTO à l'utilisateur existant
        if (userDTO.getFirst_name() != null) {
            existingUser.setFirst_name(userDTO.getFirst_name());
        }
        if (userDTO.getLast_name() != null) {
            existingUser.setLast_name(userDTO.getLast_name());
        }
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPhone() != null) {
            existingUser.setPhone(userDTO.getPhone());
        }
        if (userDTO.getProfile() != null) {
            existingUser.setProfile(userDTO.getProfile());
        }

        // Vérifier le centre uniquement s'il est spécifié dans le DTO
        if (userDTO.getCentreId() != null) {
            Centre centre = centreRepository.findById(userDTO.getCentreId()).orElse(null);
            existingUser.setCentre(centre);
        }

        // Enregistrer les modifications
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }
  //  public List<UserDTO> getUsersByIds(List<Long> ids) {
    //      List<User> users = userRepository.findAllById(ids);
    //    return users.stream()
    //           .map(userMapper::toDTO)
    //           .toList();
    // }

}
