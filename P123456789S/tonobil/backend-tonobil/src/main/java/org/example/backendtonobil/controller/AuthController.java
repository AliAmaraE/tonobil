package org.example.backendtonobil.controller;

import org.example.backendtonobil.entity.Owner;
import org.example.backendtonobil.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private OwnerRepository ownerRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Owner owner) {
        if (ownerRepository.existsByEmail(owner.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }
        Owner savedOwner = ownerRepository.save(owner);
        // Avoid returning the entire savedOwner directly to prevent infinite lazy relation JSON errors.
        return ResponseEntity.ok(Map.of(
            "id", savedOwner.getId(),
            "name", savedOwner.getName(),
            "email", savedOwner.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Owner loginRequest) {
        Optional<Owner> owner = ownerRepository.findByEmailAndPassword(
            loginRequest.getEmail(), 
            loginRequest.getPassword()
        );
        
        if (owner.isPresent()) {
            Owner o = owner.get();
            return ResponseEntity.ok(Map.of(
                "id", o.getId(),
                "name", o.getName(),
                "email", o.getEmail()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}
