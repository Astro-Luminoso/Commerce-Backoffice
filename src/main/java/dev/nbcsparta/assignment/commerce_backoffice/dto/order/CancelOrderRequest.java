package dev.nbcsparta.assignment.commerce_backoffice.dto.order;

import jakarta.validation.constraints.NotBlank;

public record CancelOrderRequest(
        @NotBlank(message = "사유는 필수입니다.") String reason
) {
}
