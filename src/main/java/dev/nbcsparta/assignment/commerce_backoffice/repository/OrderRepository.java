package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.GetOrderPageFilter;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE " +
            "(:#{#filter.orderId} IS NULL OR o.id = :#{#filter.orderId}) AND " +
            "(:#{#filter.customerName} IS NULL OR o.customer.name LIKE %:#{#filter.customerName}%) AND" +
            "(:#{#filter.status} IS NULL OR o.deliveryStatus = :#{#filter.status})")
    Page<Order> findAllByFilters(
            @Param("filter") GetOrderPageFilter filter,
            Pageable pageable
    );
}
