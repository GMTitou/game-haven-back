package com.efrei.game_haven_backend.exposition;

import com.efrei.game_haven_backend.domain.Status;
import com.efrei.game_haven_backend.config.JwtService;
import com.efrei.game_haven_backend.domain.user.User;
import com.efrei.game_haven_backend.domain.user.UserService;
import com.efrei.game_haven_backend.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

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
            String nom = null;
            String prenom = null;
            String telephone = null;
            Status status = null;

            if (principal instanceof org.springframework.security.core.userdetails.User) {
                email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
                User fullUser = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                nom = fullUser.getNom();
                prenom = fullUser.getPrenom();
                telephone = fullUser.getTelephone();
                status = fullUser.getStatus();
            }

            String token = jwtService.generateToken(authentication);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                    "email", email != null ? email : "",
                    "nom", nom != null ? nom : "",
                    "prenom", prenom != null ? prenom : "",
                    "telephone", telephone != null ? telephone : "",
                    "status", status != null ? status.toString() : "UNKNOWN"
            ));

            return response;

        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public Map<String, String> logout() {
        SecurityContextHolder.clearContext();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Déconnexion réussie");
        return response;
    }

}
