package dev.nbcsparta.assignment.commerce_backoffice.repository;

import dev.nbcsparta.assignment.commerce_backoffice.dto.order.GetOrderPageFilter;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.RecentOrderItem;
import dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.OrderDashboard;
import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE " +
            "(:#{#filter.orderId} IS NULL OR o.id = :#{#filter.orderId}) AND " +
            "(:#{#filter.customerName} IS NULL OR o.customer.name LIKE %:#{#filter.customerName}%) AND" +
            "(:#{#filter.status} IS NULL OR o.deliveryStatus = :#{#filter.status})")
    Page<Order> findAllByFilters(
            @Param("filter") GetOrderPageFilter filter,
            Pageable pageable
    );

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.data.OrderDashboard(" +
            "COUNT(o)," +
            "SUM(CASE WHEN o.orderDate BETWEEN :start AND :end THEN 1 ELSE 0 END)," +
            "COALESCE(SUM(o.totalPrice), 0)," +
            "COALESCE(SUM(CASE WHEN o.orderDate BETWEEN :start AND :end THEN o.totalPrice ELSE 0 END), 0)," +
            "SUM(CASE WHEN o.deliveryStatus = dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus.PENDING THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN o.deliveryStatus = dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus.PROCESSING THEN 1 ELSE 0 END)," +
            "SUM(CASE WHEN o.deliveryStatus = dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus.PROCESSED THEN 1 ELSE 0 END))" +
            "FROM Order o")
    OrderDashboard getStatistics(
            @Param("start")LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT new dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard.RecentOrderItem(" +
            "o.id, c.name, p.name, o.totalPrice, o.deliveryStatus) " +
            "FROM Order o " +
            "JOIN o.customer c " +
            "JOIN o.product p " +
            "ORDER BY o.orderDate DESC, o.id DESC ")
    List<RecentOrderItem> getRecentOrder(Pageable pageable);
}
