package org.example.backendtonobil.repository;

import org.example.backendtonobil.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
