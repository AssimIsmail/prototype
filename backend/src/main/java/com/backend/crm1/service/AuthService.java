package com.backend.crm1.service;

import com.backend.crm1.dto.UserDTO;
import com.backend.crm1.mapper.UserMapper;
import com.backend.crm1.model.User;
import com.backend.crm1.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }

        if (userDTO.getRole() == null) {
            throw new IllegalStateException("Role must be provided");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        String token = generateToken(savedUser.getEmail());

        UserDTO registeredAgentDTO = userMapper.toDTO(savedUser);
        registeredAgentDTO.setToken(token);
        registeredAgentDTO.setPassword(encodedPassword);

        return registeredAgentDTO;
    }


    public String generateToken(String subject) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(key)
                .compact();
    }

    public UserDTO login(String email, String password) {
        Optional<User> optionaluser = userRepository.findByEmail(email);

        if (optionaluser.isPresent()) {
            User user = optionaluser.get();

            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = generateToken(email);
                UserDTO userDTO = userMapper.toDTO(user);
                userDTO.setToken(token);
                return userDTO;
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        }

        throw new IllegalArgumentException("Agent not found with email: " + email);
    }
}
