package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.PageFilter;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE " +
            "(:#{#filter.name} IS NULL OR c.name LIKE %:#{#filter.name}%) AND " +
            "(:#{#filter.email} IS NULL OR c.email LIKE %:#{#filter.email}%) AND" +
            "(:#{#filter.status} IS NULL OR c.status = :#{#filter.status})")
    Page<Customer> findAllByFilters(
            @Param("filter") PageFilter filter,
            Pageable pageable
    );

    boolean existsByEmail(String email);
}
