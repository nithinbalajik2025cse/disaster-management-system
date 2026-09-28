package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.UserRole;
import com.example.demo.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initDefaultUsers() {
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(new User("admin", "admin123", UserRole.ADMIN, "System Administrator", "admin@disasterrelief.org"));
        }
        if (!userRepository.existsByUsername("coord1")) {
            userRepository.save(new User("coord1", "coord123", UserRole.COORDINATOR, "Relief Coordinator", "coord1@disasterrelief.org"));
        }
        if (!userRepository.existsByUsername("vol1")) {
            userRepository.save(new User("vol1", "vol123", UserRole.VOLUNTEER, "Lead Volunteer", "vol1@disasterrelief.org"));
        }
    }

    public Map<String, Object> authenticate(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Map<String, Object> response = new HashMap<>();

        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            User user = userOptional.get();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            response.put("role", user.getRole().name());
            response.put("name", user.getName());
            return response;
        }

        response.put("success", false);
        response.put("message", "Invalid username or password");
        return response;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        return userRepository.save(user);
    }
}
