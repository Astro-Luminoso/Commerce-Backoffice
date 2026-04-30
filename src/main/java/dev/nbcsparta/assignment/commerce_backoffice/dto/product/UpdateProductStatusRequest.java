package dev.nbcsparta.assignment.commerce_backoffice.dto.product;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateProductStatusRequest(
        @NotNull(message = "상품 상태는 필수입니다.")
        ProductStatus status
) {
}
