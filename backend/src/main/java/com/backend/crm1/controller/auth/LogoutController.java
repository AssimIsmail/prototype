package com.backend.crm1.controller.auth;

import com.backend.crm1.service.auth.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LogoutController {

    private final TokenBlacklistService tokenBlacklistService;

    public LogoutController(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String token = request.getHeader("Authorization").substring(7);
            tokenBlacklistService.blacklistToken(token);
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Déconnexion réussie");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
    }
}
