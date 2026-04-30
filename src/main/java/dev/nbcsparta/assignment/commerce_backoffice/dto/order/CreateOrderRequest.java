package dev.nbcsparta.assignment.commerce_backoffice.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull(message = "고객 ID는 필수입니다.")
        Long customerId,

        @NotNull(message = "상품 ID는 필수입니다.")
        Long productId,

        @NotNull(message = "수량 입력은 필수입니다.")
        @Min(value = 1, message = "수량은 1개 이상 입력해야 합니다.")
        Integer quantity
) {
}
