package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.Role;

import java.time.LocalDateTime;

public record OrderDetail(
        Long id,
        String customerName,
        String customerEmail,
        String productName,
        Integer quantity,
        Integer totalPrice,
        LocalDateTime orderDate,
        DeliveryStatus status,
        String ManagerName,
        String ManagerEmail,
        Role role
) {
    public static OrderDetail from(Order order) {
        return new OrderDetail(
                order.getId(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getDeliveryStatus(),
                order.getManager().getName(),
                order.getManager().getEmail(),
                order.getManager().getRole()
        );
    }
}
