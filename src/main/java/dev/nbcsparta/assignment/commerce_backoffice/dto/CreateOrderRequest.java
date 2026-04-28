package dev.nbcsparta.assignment.commerce_backoffice.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull
        Long customerId,

        @NotNull
        Long productId,

        @NotNull
        Integer quantity
) {
}
