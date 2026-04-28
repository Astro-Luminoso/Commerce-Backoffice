package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerDetail;
import dev.nbcsparta.assignment.commerce_backoffice.dto.GetCustomerPageFilter;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.CustomerStatusCount;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.CustomerDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerDetail(" +
            "c.id, c.name, c.email, c.phoneNumber, COUNT(o), COALESCE(SUM(o.totalPrice), 0L), c.status, c.registrationDate) " +
            "FROM Customer c LEFT JOIN Order o ON o.customer = c " +
            "WHERE (:#{#filter.name} IS NULL OR c.name LIKE %:#{#filter.name}%) AND " +
            "(:#{#filter.email} IS NULL OR c.email LIKE %:#{#filter.email}%) AND " +
            "(:#{#filter.status} IS NULL OR c.status = :#{#filter.status}) " +
            "GROUP BY c.id, c.name, c.email, c.phoneNumber, c.status, c.registrationDate")
    Page<CustomerDetail> findAllCustomerByFilters(
            @Param("filter") GetCustomerPageFilter filter,
            Pageable pageable
    );

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.CustomerDetail(" +
            "c.id, c.name, c.email, c.phoneNumber, COUNT(o), COALESCE(SUM(o.totalPrice), 0L), c.status, c.registrationDate) " +
            "FROM Customer c LEFT JOIN Order o ON o.customer = c " +
            "WHERE c.id = :customerId " +
            "GROUP BY c.id, c.name, c.email, c.phoneNumber, c.status, c.registrationDate")
    Optional<CustomerDetail> findCustomerDetail(
            @Param("customerId") Long customerId
    );

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.CustomerDashboard(" +
            "COUNT(c)," +
            "SUM(CASE WHEN c.status = dev.nbcsparta.assignment.commerce_backoffice.enumerate.AccountStatus.ACTIVE THEN 1 ELSE 0 END))" +
            "FROM Customer c")
    CustomerDashboard getStatistics();

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.charts.CustomerStatusCount(" +
            "c.status, COUNT(c)) " +
            "FROM Customer c " +
            "GROUP BY c.status")
    List<CustomerStatusCount> getStatusCount();

    boolean existsByEmail(String email);
}
