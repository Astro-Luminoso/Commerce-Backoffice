package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
