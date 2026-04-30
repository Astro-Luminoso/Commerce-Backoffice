package dev.nbcsparta.assignment.commerce_backoffice.dto.order;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;

public record GetOrderPageFilter(
        Long orderId,
        String customerName,
        ProductStatus status
) {
}
