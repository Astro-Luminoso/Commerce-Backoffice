package dev.nbcsparta.assignment.commerce_backoffice.dto.dashboard;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;

import java.time.LocalDateTime;

public record RecentOrderItem(
        String id,
        String orderNo,
        String customerId,
        String customer,
        String customerEmail,
        String productId,
        String product,
        Integer quantity,
        Integer amount,
        LocalDateTime date,
        DeliveryStatus status,
        String createdByAdminId,
        String createdByAdminName,
        String createdByAdminEmail,
        String createdByAdminRole
) {
}
