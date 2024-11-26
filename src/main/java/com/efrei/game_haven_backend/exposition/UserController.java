package com.efrei.game_haven_backend.exposition;

import com.efrei.game_haven_backend.domain.Status;
import com.efrei.game_haven_backend.domain.user.JwtService;
import com.efrei.game_haven_backend.domain.user.User;
import com.efrei.game_haven_backend.domain.user.UserService;
import com.efrei.game_haven_backend.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public User createUser(@RequestBody User user) {
        user.setMdp(passwordEncoder.encode(user.getMdp()));
        return userService.createUser(user);
    }

    @PostMapping("/authenticate")
    public Map<String, Object> authenticate(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getMdp())
            );

            Object principal = authentication.getPrincipal();

            String email = null;
            String telephone = "Non disponible";
            String prenom = "Non disponible";
            String nom = "Non disponible";
            String status = "Non disponible";

            if (principal instanceof User) {
                User userDetails = (User) principal;
                email = userDetails.getEmail();
                telephone = userDetails.getTelephone() != null ? userDetails.getTelephone() : telephone;
                prenom = userDetails.getPrenom() != null ? userDetails.getPrenom() : prenom;
                nom = userDetails.getNom() != null ? userDetails.getNom() : nom;
                status = userDetails.getStatus() != null ? String.valueOf(userDetails.getStatus()) : status;
            } else if (principal instanceof org.springframework.security.core.userdetails.User) {
                email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            }

            if (email == null) {
                throw new RuntimeException("Erreur : email non trouvé pour l'utilisateur authentifié.");
            }

            String token = jwtService.generateToken(authentication);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                    "email", email,
                    "telephone", telephone,
                    "prenom", prenom,
                    "nom", nom,
                    "status", status
            ));

            return response;

        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
}
