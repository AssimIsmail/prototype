package com.backend.crm1.controller;

import com.backend.crm1.dto.PasswordDTO;
import com.backend.crm1.dto.UserDTO;
import com.backend.crm1.model.Role;
import com.backend.crm1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${upload.dir}")
    private String uploadDir; // Directory for storing profile images

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        UserDTO user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/password")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable Long id, @RequestBody PasswordDTO passwordDTO) {

        // Logique pour mettre à jour le mot de passe
        // Par exemple, vous pouvez appeler votre service utilisateur ici
        UserDTO updatedUser=userService.updateUserPassword(id, passwordDTO.getPassword());
        return ResponseEntity.ok(updatedUser);

    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable long id,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam("role") String role,
            @RequestParam(value = "profile", required = false) MultipartFile profile) {

        // Fetch the existing user by ID
        UserDTO existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Create a new DTO object and populate it
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setFirst_name(firstName);
        userDTO.setLast_name(lastName);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);

        // Set the password only if provided
        if (password != null && !password.trim().isEmpty()) {
            userDTO.setPassword(password);
        } else {
            userDTO.setPassword(existingUser.getPassword()); // Keep existing password if not provided
        }

        userDTO.setRole(Role.valueOf(role.toUpperCase()));

        if (profile != null && !profile.isEmpty()) {
            try {
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + profile.getOriginalFilename();
                Path filePath = new File(uploadDir, fileName).toPath();

                Files.copy(profile.getInputStream(), filePath);

                userDTO.setProfile("uploads/" + fileName); // Ensure this URL is accessible by frontend
            } catch (IOException e) {
                e.printStackTrace(); // Log the exception
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            userDTO.setProfile(existingUser.getProfile());
        }

        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping(value = "/{id}/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDTO> updateUserProfile(
            @PathVariable long id,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam(value = "profile", required = false) MultipartFile profile) {

        // Fetch the existing user by ID
        UserDTO existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Create a new DTO object and populate it
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setFirst_name(firstName);
        userDTO.setLast_name(lastName);
        userDTO.setEmail(email);
        userDTO.setPhone(phone);

        // Check if a new profile picture has been provided
        if (profile != null && !profile.isEmpty()) {
            // Handle saving the new profile image
            try {
                // Create the directory if it doesn't exist
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Create a unique file name
                String fileName = System.currentTimeMillis() + "_" + profile.getOriginalFilename();
                Path filePath = new File(uploadDir, fileName).toPath();

                // Save the profile picture
                Files.copy(profile.getInputStream(), filePath);

                // Set the profile image URL in the userDTO
                userDTO.setProfile("uploads/" + fileName); // Ensure this URL is accessible by frontend
            } catch (IOException e) {
                e.printStackTrace(); // Log the exception
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Keep the existing profile image if no new one is provided
            userDTO.setProfile(existingUser.getProfile());
        }

        // Update the user profile details in the service
        UserDTO updatedUserProfile = userService.updateUserProfile(id, userDTO);
        return ResponseEntity.ok(updatedUserProfile);
    }
    // @PostMapping("/batch")
    //public ResponseEntity<List<UserDTO>> getUsersByIds(@RequestBody List<Long> ids) {
    //    List<UserDTO> users = userService.getUsersByIds(ids);
    //  return ResponseEntity.ok(users);
    // }
}
