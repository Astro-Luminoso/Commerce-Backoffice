package dev.nbcsparta.assignment.commerce_backoffice.dto.order;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull(message = "배송상태 입력은 필수입니다.")
        DeliveryStatus status
) {
}
