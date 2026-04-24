package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Managers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerAuthRepository extends JpaRepository<Managers, Long> {
}
