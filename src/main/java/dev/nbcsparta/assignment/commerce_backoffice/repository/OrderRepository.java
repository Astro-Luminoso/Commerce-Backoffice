package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE " +
            "(:orderId IS NULL OR o.id = :orderId) AND " +
            "(:customerName IS NULL OR o.customer.name LIKE %:customerName%) AND" +
            "(:status IS NULL OR o.deliveryStatus = :status)")
    Page<Order> findAllByFilters(
            @Param("orderId") Long orderId,
            @Param("customerName") String customerName,
            Pageable pageable,
            @Param("status") ProductStatus status
    );

}
