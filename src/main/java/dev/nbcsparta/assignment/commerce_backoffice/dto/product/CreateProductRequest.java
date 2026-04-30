package dev.nbcsparta.assignment.commerce_backoffice.dto.product;

import dev.nbcsparta.assignment.commerce_backoffice.enumerate.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(
        @NotBlank(message = "상품 이름은 필수입니다.")
        String name,

        @NotBlank(message = "카테고리 이름은 필수입니다.")
        String category,

        @NotNull(message = "가격 입력은 필수입니다.")
        @Min(value = 0, message = "가격은 0 이상으로 입력해야 합니다.")
        int price,

        @NotNull(message = "수량 입력은 필수입니다.")
        @Min(value = 1, message = "수량은 1개 이상 입력해야 합니다.")
        int quantity,

        @NotNull(message = "상품 상태는 필수입니다.")
        ProductStatus status
) {
}
