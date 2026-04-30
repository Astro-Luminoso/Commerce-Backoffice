package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerAuthRepository extends JpaRepository<Manager, Long> {

    boolean existsByEmail(String email);

    Optional<Manager> findByEmail(String email);
}
