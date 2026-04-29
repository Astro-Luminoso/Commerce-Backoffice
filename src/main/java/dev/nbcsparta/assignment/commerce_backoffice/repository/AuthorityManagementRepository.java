package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.HasAuthority;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityManagementRepository extends JpaRepository<HasAuthority, Long> {

    void removeAllByManager(Manager manager);
}
