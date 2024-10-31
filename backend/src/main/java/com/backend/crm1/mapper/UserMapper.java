package com.backend.crm1.mapper;

import com.backend.crm1.dto.UserDTO;
import com.backend.crm1.model.Centre;
import com.backend.crm1.model.Role;
import com.backend.crm1.model.User;
import com.backend.crm1.repository.CentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private CentreRepository centreRepository;

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirst_name(user.getFirst_name());
        dto.setLast_name(user.getLast_name());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setProfile(user.getProfile());
        dto.setRole(user.getRole()); // Convertit l'énumération en chaîne
        if (user.getCentre() != null) {
            dto.setCentreId(user.getCentre().getId());
        }
        return dto;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();

        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());
        user.setEmail(userDTO.getEmail());

        // Vérification du mot de passe : ne pas inclure le mot de passe lors de la mise à jour sauf s'il est spécifié
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(userDTO.getPassword());
        }

        user.setPhone(userDTO.getPhone());
        user.setProfile(userDTO.getProfile());

        // Vérifiez si le rôle est spécifié, sinon définissez-le par défaut
        if (userDTO.getRole() == null) {
            user.setRole(Role.USER); // Définit le rôle par défaut ici
        } else {
            user.setRole(userDTO.getRole()); // Pas besoin de conversion, c'est déjà un Role
        }

        if (userDTO.getCentreId() != null) {
            Centre centre = centreRepository.findById(userDTO.getCentreId()).orElse(null);
            user.setCentre(centre);
        }
        return user;
    }

}
