package dev.nbcsparta.assignment.commerce_backoffice.dto;

import dev.nbcsparta.assignment.commerce_backoffice.entity.Order;
import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;

import java.time.LocalDateTime;

public record CreateOrderResponse(
        Long id,
        String customerName,
        String productName,
        Integer quantity,
        Long totalPrice,
        LocalDateTime orderDate,
        DeliveryStatus status,
        String ManagerName
) {
    public static CreateOrderResponse from(Order order) {
        return new CreateOrderResponse(
                order.getId(),
                order.getCustomer().getName(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getDeliveryStatus(),
                order.getManager().getName()
        );
    }

}
