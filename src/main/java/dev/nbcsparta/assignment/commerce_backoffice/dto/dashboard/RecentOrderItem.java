package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;

public record RecentOrderItem(
        Long id,
        String customerName,
        String productName,
        Integer quantity,
        DeliveryStatus status
) {
}
