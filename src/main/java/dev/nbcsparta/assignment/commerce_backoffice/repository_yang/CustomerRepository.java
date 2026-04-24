package dev.nbcsparta.assignment.commerce_backoffice.repository_yang;

import dev.nbcsparta.assignment.commerce_backoffice.entity_yang.Customer;
import dev.nbcsparta.assignment.commerce_backoffice.enum_yang.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Page<Customer> findAllByFilters(String keyword, Pageable pageable, CustomerStatus status);
}
