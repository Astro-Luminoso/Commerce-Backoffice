package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.HasAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityManagementRepository extends JpaRepository<HasAuthority, Long> {
}
